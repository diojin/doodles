<%@ page import="outerhaven.dao.*" %>
<%@ page import="org.springframework.context.support.ClassPathXmlApplicationContext" %>
<html>
<body>
<h2><%="Hello World!" %></h2>
<h2>
<%
	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	TestDao dao = (TestDao)ctx.getBean("testDao");
	out.println(dao.test4());
%></h2>
</body>
</html>
