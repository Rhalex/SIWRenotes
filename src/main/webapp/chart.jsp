<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>FusionCharts</title>
	<!-- Font Awesome -->
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
  <!-- Material Design Bootstrap -->
 
  <link href="css/mdb.min.css" rel="stylesheet">
  <!-- Your custom styles (optional) -->
  <link href="css/style.min.css" rel="stylesheet">
	<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
	
	<script type="text/javascript" src="fusionChartsSuitXT/js/fusioncharts.js"></script>
	<script type="text/javascript" src="fusionChartsSuitXT/js/themes/fusioncharts.theme.fusion.js"></script>

	<script type="text/javascript" src="js/diagrams.js"></script>
	
</head>
<body onload="setDia1('pie3d'); setDia2('column3d'); setDia3('bar2d'); setDia4('line')">
      
	<div align="center" class="row"> 
		<div id="chart-container"></div>
		
		<button class="btn" onclick="setDia1('pie2d')">PIE 2D</button>
		<button class="btn" onclick="setDia1('pie3d')">PIE 3D</button>
		<button class="btn" onclick="setDia1('doughnut2d')">DONUT 2D</button>
		<button class="btn" onclick="setDia1('doughnut3d')">DONUT 3D</button>
	</div>
	<br>
	<div align="center" class="row"> 
		<div id="chart-container2"></div>
		
		<button class="btn" onclick="setDia2('column2d')">COLUMN 2D</button>
		<button class="btn" onclick="setDia2('column3d')">COLUMN 3D</button>
	</div>
	<br>
	<div align="center" class="row"> 
		<div id="chart-container3"></div>
		
		<button class="btn" onclick="setDia3('bar2d')">BAR 2D</button>
		<button class="btn" onclick="setDia3('bar3d')">BAR 3D</button>
	</div>
	<br>
	<div align="center" class="row"> 
		<div id="chart-container4"></div>
		
		<button class="btn" onclick="setDia4('line')">LINE 2D</button>
		<button class="btn" onclick="setDia4('area2d')">AREA 2D</button>
	</div>

</body>
</html>