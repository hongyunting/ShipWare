<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
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
					<a class="dropdown-toggle" href="ViewContainer.jsp">
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
                    <h3 class="page-header"><a class="btn btn-social btn-danger" style="margin-right:20px;"><i class="fa fa-angle-left"></i>    Back </a>Add New Container </h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
            <div class="row">
            	<div class="panel panel-default">
                	<div class="panel-heading">
                    	Container Information
                    </div>
                    <form method="post" action="containers">
	                    <div class="panel-body">
	                    	<div class="form-group">
	                        	<label>Container Name</label>
	                            <input class="form-control" placeholder="Enter Container Name" name="cName">
	                            <p class="help-block">Example block-level help text here.</p>
	                    	</div>
	                    	<div class="form-group">
	                        	<label>Host Name</label>
	                            <input class="form-control" placeholder="Enter Host Name" name= "hName">
	                            <p class="help-block">Example block-level help text here.</p>
	                    	</div>
	                    	<label>Weight</label>
	                    	<div class="form-group input-group">
	                            <input type="text" class="form-control" name="cWeight">
	                            <span class="input-group-addon">KG</span>
	                    	</div>
	                    	<div class="form-group">
	                        	<label>Port Name</label>
	                            <input class="form-control" placeholder="Enter Port Name" name="pName">
	                            <p class="help-block">Example block-level help text here.</p>
	                    	</div>
	                    	<input type="submit" value="Add Container" class="btn btn-success"/>
	                    </div>
                    </form>
                </div>
            </div>
        </div>
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
<%--     <%
    
	    final String host = "localhost";
		final int portNumber = 9000;
		System.out.println("Creating socket to '" + host + "' on port " + portNumber);
	
		while (true) {
			Socket socket = new Socket(host, portNumber);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out1 = new PrintWriter(socket.getOutputStream(), true);
	
			System.out.println("server says:" + br.readLine());
	
			BufferedReader userInputBR = new BufferedReader(new InputStreamReader(System.in));
			String userInput = userInputBR.readLine();
	
			out.println(userInput);
	
			System.out.println("server says:" + br.readLine());
	
			if ("exit".equalsIgnoreCase(userInput)) {
				socket.close();
				break;
			}
		}

    %> --%>
    
<%--     <% 
        try{
            int character;
            Socket socket = new Socket("localhost", 9000);

            InputStream inSocket = socket.getInputStream();
            OutputStream outSocket = socket.getOutputStream();

            String str = "Hello!\n";
           	byte buffer[] = str.getBytes();
            outSocket.write(buffer);

            while ((character = inSocket.read()) != -1) {
                out.print((char) character);
            }

            socket.close();
        }
        catch(java.net.ConnectException e){
        %>
            You must first start the server application (RunServer.java) at the command prompt.
        <%
        }
        %> --%>
          
    
</body>
</html>