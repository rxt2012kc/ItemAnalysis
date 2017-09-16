---使用流程---
·进入virtualenv
Linux下： $ source venv/scripts/activate
windows下：$ venv\scripts\activate

·运行
1\
--cd flaskr
--python flaskr.py
	得到运行结果Running on http://127.0.0.1:5000/
	访问http://127.0.0.1:5000/
....
   也可以直接在setting设置好python路径后用pycharm运行啦
2\
--访问后会被重定向到http://127.0.0.1:5000/login/
--注册或者登录就好


·flaskr中的文件结构
|controller  #处理请求时使用的业务函数（类）
	|loginController.py  #处理登录和注册请求
|models	#数据库操作用到的ORM类,一个对应一张表
	|users.py   
	|entries.py #只是实验的时候放着用的
|static	#代码中要用到的静态资源
	|css
	|js
	|images
|templates	#模板，即前端文件
	|index.html
	|login.html
|config.py	#框架的配置文件
|database.py	#连接Mysql
|flaskr.py	#主要文件，后台入口，url处理
|middleware.py	#中间件，在url跳转之前进行一些条件检查
|*.sql		#建表的sql语句，方便用

附注：flaskr.py的__main__底下注释里有一些实验sqlalchemy操作数据库时候的函数。。。