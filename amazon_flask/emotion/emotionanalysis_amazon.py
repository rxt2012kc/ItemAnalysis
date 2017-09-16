# -*- coding:utf-8 -*-#
"""
Implementation of Sentiment Analysis for Amazon Reviews
@author renxintao
@date 2017/09/14
"""
import tensorflow as tf
import numpy as np
import json
import urllib

np.random.seed(1337)  # Random seed
import sys

# 超参数
valid_rate = 0
seed = 1
lr = 0.001


# 初始化CNN卷积器的权重
def weight_variable(shape):
    initial = tf.truncated_normal(shape, stddev=0.1)
    return tf.Variable(initial)


# 初始化CNN卷积器的偏置
def bias_variable(shape):
    initial = tf.constant(0.1, shape=shape)
    return tf.Variable(initial)


# 计算模型准确率
def compute_accuracy(v_xs, v_ys):
    global scores
    prediction = sess.run(scores, feed_dict={xs: v_xs, keep_prob: 1})
    y_pre = tf.nn.softmax(prediction)
    correct_prediction = tf.equal(tf.argmax(y_pre, 1), tf.argmax(v_ys, 1))
    accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))
    result = sess.run(accuracy, feed_dict={xs: v_xs, ys: v_ys, keep_prob: 1})
    return result


def build_data(data_json):
    """
    Loads sentence data ;
    @param data_json: 爬取到的亚马逊评论信息构成的json
    @return:
        revs：返回每句评论及评论的长度,
        vocab：返回每个词的词频
    """
    revs = []
    if data_json == "":
        # 测试用句子
        data_json = '{"detail":[{"date":"2017.09.11","format":"kindle","review": "Dresses rarely fit me well' \
                    ' because of a large bust. I\'m normally a 14 and the large fits me like it was made for me. ' \
                    'It is very pretty and summery in color. The skirt half hits my mid knee which is perfect' \
                    'for work. This is a no wrinkle, cheerful dress with just the right sleeve. Th Princess' \
                    ' seams give this knit shape and form. I really love it, it is a new favorite.I want to' \
                    ' add that this is quite heavy with essentially 2 layers of knit forming the fabric."},' \
                    '{"date":"2017.09.12","format":"paper","review":"yy"}]}'
    data = json.loads(data_json)
    details = data["detail"]
    sen_len = len(details)
    vocab = {}
    count = 0
    for i in range(sen_len):
        line = details[i]["review"]
        words = set(line.strip().split(' '))
        review = line.strip().split(' ')
        for word in words:
            try:
                vocab[word] += 1  # 词频统计
            except:
                vocab[word] = 1
        datum = {
            "text": line.strip(),
            "num_words": len(review)
        }
        revs.append(datum)
        count += 1
    return revs, vocab, data


def load_bin_vec(fname, vocab):
    """
    Loads 300x1 word vectors from pre-training word2vec
    """
    word_vecs = {}
    with open(fname, "rb") as f:
        header = f.readline()  # 第一行是词数和向量长度
        vocab_size, layer1_size = map(int, header.split())
        for line in xrange(vocab_size):
            word = []
            while True:  # 逐个字符读取词
                ch = f.read(1)
                if ch == ' ':
                    word = ''.join(word)  # 读完一个词，把它拼接起来（分隔符不存在）
                    break
                if ch != '\n':
                    word.append(ch)

            # 读取词对应的词向量
            vec = f.readline().strip().split(" ")
            try:
                word_vecs[word] = np.array(vec, dtype='float32')
            except:
                pass
    return word_vecs  # 返回词及对应的词向量

# 对于在预训练好的word2vec中没有的词，进行随机均匀分布初始化
def add_unknown_words(word_vecs, vocab, min_df=1, w2v_dim=300):
    cnt = 0.0;
    for word in vocab:
        if vocab[word] >= min_df:
            try:
                word = word_vecs[word]
            except:
                # 为不在word_vecs中的单词构造随机值
                word_vecs[word] = np.random.uniform(-0.25, 0.25, w2v_dim)
                cnt += 1
    return cnt


def get_W(word_vecs, w2v_dim=300):
    """
    Get word matrix. W[i] is the vector for word indexed by i
    造一个词矩阵，把所有词向量放入矩阵，并保存word->index映射
    """
    vocab_size = len(word_vecs)
    word_idx_map = dict()
    W = np.zeros(shape=(vocab_size + 1, w2v_dim))
    W[0] = np.zeros(w2v_dim)
    i = 1
    for word in word_vecs:
        W[i] = word_vecs[word]
        word_idx_map[word] = i
        i += 1
    return W, word_idx_map

# 加载数据
def load_data(max_len, revs, W, word_idx_map, filter_h=5, model_type="CNN"):
    w2v = W
    revs_size = len(revs)
    pad = filter_h - 1  # padding的圈数

    np.random.seed(1)
    np.random.shuffle(revs)

    input_shape = []
    input_shape.append(max_len + 2 * pad)  # 输入规模 = (最大句子词数+padding)*词向量长
    input_shape.append(w2v_dim)
    w2v[0] = np.zeros(w2v_dim)  # 当初构造的时候第一行就是全0啊。。。
    data = np.empty((revs_size, (max_len + 2 * pad), w2v_dim), dtype="float32")
    for i in range(revs_size):  # 将微博数据转矩阵存入data
        rev = revs[i]
        s2v = get_sen_w2v(rev["text"], word_idx_map, w2v, max_len, filter_h)  # 句子转词向量
        data[i, :, :] = s2v

    return input_shape, data


def get_sen_w2v(sent, word_idx_map, w2v, max_len, filter_h=5):
    words = sent.split()
    s2v = []
    pad = filter_h - 1
    cnt = 0
    for i in xrange(pad):  # padding
        s2v.append(w2v[0])
    for word in words:
        if cnt >= max_len:  # 截断长度大于模型的句子
            break
        cnt += 1
        try:
            s2v.append(w2v[word_idx_map[word]])
        except:
            pass

    while len(s2v) < max_len + 2 * pad:  # padding
        s2v.append(w2v[0])
    return s2v

if __name__ == '__main__':
    try:
        data = sys.argv[1]
        data = urllib.unquote(data)  # 将字符串编码，避免方/花括号等符号被shell特殊处理
    except:
        data = ""
    # 得到每个词的句频、每个评论一个dict（文本、标签、词数）
    revs, vocab, input_data = build_data(data)

    # 词向量长度和最大句子长度
    w2v_dim = 300
    max_l = 276

    # 经过word2vec训练好的词向量存放路径
    w2v_file = "/home/renxintao/amazon/data/3_classAmazon_test.w2v"
    w2v = load_bin_vec(w2v_file, vocab)  # 从二进制文件中获取句频统计中出现的词的词向量
    not_in_vocab = add_unknown_words(w2v, vocab)  # 给不在w2v结果中的词构造词向量（均匀分布）

    W, word_idx_map = get_W(w2v)
    rand_vecs = {}
    add_unknown_words(rand_vecs, vocab)
    W2, _ = get_W(rand_vecs)

    # 对输入数据构造词向量矩阵并padding
    input_shape, data = load_data(max_l, revs, W, word_idx_map, filter_h=5, model_type="CNN")
    data_map = np.arange(0, len(revs), 1)  # 一个shape为(data长度，1)的map，与data一起被打乱，用来标记data对应的index

    # xs, ys为tensorflow输入占位
    xs = tf.placeholder(tf.float32, [None, input_shape[0], 300])  # 输入是句子长度*300的矩阵就是input_shape[0],也是maxl
    ys = tf.placeholder(tf.float32, [None, 3])  # 输出是OneHot的1*3编码
    keep_prob = tf.placeholder(tf.float32, name="keep_prob")
    pooled_outputs = []

    for filter_size in range(3, 6, 1):  # 3*300, 4*400, 5*500的卷积核
        W = weight_variable([filter_size, w2v_dim, w2v_dim])  # filters数量 = 词向量长度
        b = bias_variable([w2v_dim])
        conv = tf.nn.conv1d(xs, W, stride=1, padding='VALID', name='conv')  # 卷积，步长为1，1*（maxl-filter_size+1)*300
        h = tf.nn.relu(tf.nn.bias_add(conv, b), name='relu')
        h_ = h[:, :, np.newaxis, :]  # 1*（maxl-filter_size+1)*1*300
        pooled = tf.nn.max_pool(h_,
                                ksize=[1, input_shape[0] - filter_size + 1, 1, 1],  # 整个卷积的结果做max_pooling
                                strides=[1, 1, 1, 1],
                                padding='VALID',
                                name="pool")
        pooled_outputs.append(pooled)  # 串接池化的结果作为新输入,1*1*1*300

    num_filters_total = w2v_dim * 3
    h_pool = tf.concat(pooled_outputs, 3)  # 在第3维度（300）上拼接张量
    h_pool_flat = tf.reshape(h_pool, [-1, num_filters_total])  # 转线性序列
    h_drop = tf.nn.dropout(h_pool_flat, keep_prob)
    W = tf.get_variable("W",
                        shape=[num_filters_total, 3],
                        initializer=tf.contrib.layers.xavier_initializer())

    b = tf.Variable(tf.constant(0.1, shape=[3]), name="b")
    scores = tf.nn.xw_plus_b(h_drop, W, b, name="scores")
    sess = tf.Session()
    saver = tf.train.Saver()

    # shuffle数据
    np.random.seed(seed)
    np.random.shuffle(data)
    np.random.seed(seed)
    np.random.shuffle(data_map)
    datasize = len(revs)
    test_data = data[int(datasize * valid_rate):]
    test_map = data_map[int(datasize * valid_rate):]

    # 读取预先训练好的模型参数
    saver.restore(sess, "/home/renxintao/amazon/Chinese/save/Amazon_3_class_30w.model-29")

    # 将数据输入模型中进行运算
    for e in range(datasize):
        prediction = sess.run(scores, feed_dict={xs: test_data[e:e + 1], keep_prob: 1})
        y_pre = tf.nn.softmax(prediction)
        y_pre = sess.run(tf.argmax(y_pre, 1))  # 返回的是one hot编码中最大值的索引号作为分类结果
        loc = test_map[e]
        input_data["detail"][loc]['label'] = int(y_pre[0])  # 根据映射为输入数据的评论增加标签

    # 返回json
    return_str = json.dumps(input_data)
    print return_str
