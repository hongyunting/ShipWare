<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	    <div class="container">
	    <div class="row">
        	<img src="images/Shipware.png" style="max-height:300px; max-width:300px; margin-bottom:-55px; margin-left:430px; margin-top:50px;"/>
        </div>
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Please Sign In</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form" method="post" action="Login">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Username" name="username" type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name="password" type="password" value="">
                                </div>
                                <div class="checkbox">
                                    <label>
                                        <input name="remember" type="checkbox" value="Remember Me">Remember Me
                                    </label>
                                </div>
                                <!-- Change this to a button or input when using this as a form -->
                                <input type="submit" class="btn btn-lg btn-success btn-block" text="Login"/>
                            </fieldset>
                        </form>
                    </div>
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
</body>
</html>