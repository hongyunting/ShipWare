<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ page import="java.io.*, java.net.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Remote Access of Stowage Planning Functions</title>

    <!-- Bootstrap Core CSS -->
    <link href="../ShipWare/css/bootstrap.min.css" rel="stylesheet" media="screen">

    <!-- MetisMenu CSS -->
    <link href="../ShipWare/css/metisMenu.min.css" rel="stylesheet" media="screen">

    <!-- Custom CSS -->
    <link href="../ShipWare/css/sb-admin-2.css" rel="stylesheet" media="screen">

	<!-- Social Buttons CSS -->
    <link href="../Shipware/vendor/bootstrap-social.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="../ShipWare/css/morris.css" rel="stylesheet" media="screen">

    <!-- Custom Fonts -->
    <link href="../ShipWare/css/font-awesome.min.css" rel="stylesheet" type="text/css" media="screen">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body>
	<div id="wrapper">
		<!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="ViewContainer.jsp"><img src="images/Shipware.png" style="max-width: 123px;margin-top: -12px;margin-left: 15px;"/>
                </a>
            </div>
            <!-- /.navbar-header -->

			<ul class="nav navbar-top-links navbar-left">
				<li>
					<a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-th fa-fw"></i> CONTAINER</i>
                    </a>
				</li>
				<li>
					<a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i> ACCOUNT</i>
                    </a>
				</li>
				<li>
					<a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-chain fa-fw"></i> REGISTRIES</i>
                    </a>
				</li>
				
			</ul>
            <ul class="nav navbar-top-links navbar-right">
                <!-- /.dropdown -->
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
                        </li>
                        <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="login.html"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->
            
            </nav>
        
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">Containers</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <a class="btn btn-social btn-primary"><i class="fa fa-refresh"></i>    Refresh </a>
            <a class="btn btn-social btn-success" href="containers.jsp"><i class="fa fa-plus"></i>    Deploy Container </a>
            <br /><br />
            
            <%
			    String[] authors = request.getParameterValues("author");
			    if (authors != null) {
			    	
			    }
			  %>
			  <%@page import = "java.sql.*"  %>
			  <%
				  Class.forName("com.mysql.jdbc.Driver");
				  Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/shipWareDB", "root", "iwillnotforget");
			      Statement stmt = conn.createStatement();
			 
			      String sqlStr = "SELECT * FROM containers";
				
			      ResultSet rset = stmt.executeQuery(sqlStr);
			  %>
			  
		        <table border=1 cellpadding=5 class="table table-striped table-bordered table-hover" id="allContainers">
		          <tr>
		            <th>Container ID</th>
		            <th>Name</th>
		            <th>Host</th>
		            <th>Weight</th>
		            <th>Port Name</th>
		            <th>Actions</th>
		          </tr>
		          
		       <%
			      while (rset.next()) {
			  %>
			  
				  <tr>
		            <td><%= rset.getString("containerId") %></td>
		            <td><%= rset.getString("containerName") %></td>
		            <td><%= rset.getString("hostName") %></td>
		            <td><%= rset.getInt("weight") %>KG</td>
		            <td><%= rset.getString("portName") %></td>
		            <td><a class="btn btn-social btn-default"><i class="fa fa-search"></i></a></td>
		          </tr>
		          
		      <%
			      }
			  %>
			  
			  </table>
		        <br>
		      
        </div>
        
        <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p>Copyright &copy; Shipware 2016</p>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        </footer>
	</div>
	
	
	
	    <!-- jQuery -->
    <script src="../ShipWare/vendor/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../ShipWare/vendor/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../ShipWare/vendor/metisMenu.min.js"></script>

    <!-- Morris Charts JavaScript -->
    <script src="../ShipWare/vendor/raphael.min.js"></script>
    <script src="../ShipWare/vendor/morris.min.js"></script>
    <script src="../ShipWare/data/morris-data.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../ShipWare/dist/sb-admin-2.min.js"></script>
</body>
</html>