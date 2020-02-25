<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta charset="utf-8">
<title>navbar</title>
<!-- Font Awesome -->
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
  <!-- Bootstrap core CSS -->
  <link href="css/bootstrap.min.css" rel="stylesheet">
  <!-- Material Design Bootstrap -->
  <link href="css/mdb.min.css" rel="stylesheet">
  <!-- Your custom styles (optional) -->
  <link href="css/style.min.css" rel="stylesheet">
	<!-- SCRIPTS -->
  <!-- JQuery -->
  <!--  <script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>-->
 
  <script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.js"></script>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.12.4.js"></script>
  <script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 
  <script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-autocomplete/1.0.7/jquery.auto-complete.js"
	integrity="sha256-K3qK8ynOxhJVloLac0CTWwr7iFKVDZF4Gd2yEsiAZYA="
	crossorigin="anonymous"></script>
 
  <!-- Bootstrap tooltips -->
  <script type="text/javascript" src="js/popper.min.js"></script>
  <!-- Bootstrap core JavaScript -->
  <script type="text/javascript" src="js/bootstrap.min.js"></script>
  <!-- MDB core JavaScript -->
  <script type="text/javascript" src="js/mdb.min.js"></script>
  
   <script type="text/javascript" src="js/javaScriptUtility.js"></script>
</head>
<body>
	<header>
<!-- NavBar -->
  <nav class="navbar fixed-top navbar-expand-lg navbar-dark scrolling-navbar">
    <div class="container">

      <!-- Brand -->
      <form action="ViewAdList" method="get" class="mx-5">
        	<input type="image" src= "bootstrap2/resources/icon1_white.png" width="60" height="60" class="pull-right">
      </form>

      <!-- Collapse -->
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
        aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <!-- Links -->
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <!-- Left -->
        <ul class="navbar-nav mr-auto">
          <li class="nav-item">
			<form class="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-auto my-auto my-md-auto" action="ViewAdList" method="get">
     			<div class="input-group dropdown is-active dropdown-trigger">
				        <div class="md-form mt-2 mb-2">
      				 			<input type="text" class="input select" placeholder="Inserisci il Titolo" aria-label="Search" aria-describedby="basic-addon2" name="titolo" id="aTitolo" autocomplete=off />
							<div class="dropdown-menu" id="dropdown-menu" role="menu"></div>
							
					</div>
      			</div>
			</form>
          </li>
        </ul>

        <!-- Right -->
        <ul class="navbar-nav nav-flex-icons">

          <li class="nav-item">
            <a class="nav-link" href="cart">
              <i class="fas fa-cart-arrow-down"></i>
            </a>
          </li>
		  <li class="nav-item">
            <a class="nav-link" href="profile.jsp">
              <i class="fas fa-user"></i>
            </a>
          </li>
          
        </ul>

      </div>

    </div>
  </nav>
 <script>
 $( "#aTitolo" ).keyup(function() {
		var value = $.value;
		if (value==null){
			if(document.getElementById("suggestion"))
    		{document.getElementById("suggestion").remove()};
    		return '';
		};
	});
		var xhr;
		var suggestValue;
		$('input[name="titolo"]').autoComplete({
			minChars: 1,
			cache: false,
		    source: function(term, response){
		        xhr = $.getJSON('Autocomplete', { titolo: term }, function(data){ response(data); });
		        suggestValue = $.textContent; 
		       	if(suggestValue == null){
		       		if(document.getElementById("suggestion"))
		       		{document.getElementById("suggestion").remove()};
		       		return '';
		       	}
		    },
		     /* _renderMenu: function( ul, items ) {
		    	  var that = this;
		    	  $.each( items, function( index, item ) {
		    	    that._renderItemData( ul, item );
		    	  });
		    	} */
		    renderItem: function (item, search){
				
		    	if(document.getElementById("suggestion"))
		    		{document.getElementById("suggestion").remove()};
		    		
		    	var t = document.createElement("div");
		    	t.id = "suggestion";
		    	t.className += "dropdown-item";
		    	t.append(item);
		    	document.getElementById("dropdown-menu").append(t);
		    	var god =  document.getElementById("dropdown-menu");
		    	console.log(god);
		    	return '';
		    	
		    }
		});
		$(document).on('click', "[id^='suggestion']", function () {
			document.getElementById("aTitolo").value = document.getElementById("suggestion").textContent;
			document.getElementById("suggestion").remove();
		});
 </script>
  <!-- Navbar -->

</header>
</body>
</html>
