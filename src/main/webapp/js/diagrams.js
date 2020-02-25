/**
 * 
 */

function setDia1(ct)
{
	var chartType = ct;
	if(!ct)
		chartType = 'pie3d';
	
	$.ajax({
	    url: "viewProfileDiagrams",
	    type: "POST",
	    async: true,
	    success: function(data)
	    {
	    	const chartData = data;
	
	        const chartConfig = {
	        type: chartType,
	        renderAt: 'chart-container',
	        width: '100%',
	        height: '400',
	        dataFormat: 'json',
	        dataSource: {
	        	
	            "chart": {
	                "caption": "Distribuzione Stelle",
	                "enableSmartLabels": "1",
	                "startingAngle": "0",
	                "showPercentValues": "0",
	                "decimals": "1",
	                "useDataPlotColorForLabels": "1",
	                "theme": "fusion",
	                },
	           
	            "data": chartData
	            }
	        };
	       
	        FusionCharts.ready(function(){
	        var fusioncharts = new FusionCharts(chartConfig);
	        fusioncharts.render();
	        });
	        
	    },
	    error: function()
	    {
	        alert("Impossibile visualizzare i dati");
	    }
	});

}

function setDia2(ct)
{
	var chartType = ct;
	if(!ct)
		chartType = 'column3d';
	
	$.ajax({
	    url: "viewProfileDiagrams",
	    type: "GET",
	    async: true,
	    success: function(data)
	    {
	    	const chartData = data;
	
	        const chartConfig = {
	        type: chartType,
	        renderAt: 'chart-container2',
	        width: '100%',
	        height: '400',
	        dataFormat: 'json',
	        dataSource: {
	        	
	            "chart": {
	            	"caption": "Valutazioni Inserzioni",
	                "xAxisName": "Inserzione",
	                "yAxisName": "Valutazione",
	                "theme": "fusion"
	                },
	           
	            "data": chartData
	            }
	        };
	       
	        FusionCharts.ready(function(){
	        var fusioncharts = new FusionCharts(chartConfig);
	        fusioncharts.render();
	        });
	        
	    },
	    error: function()
	    {
	        alert("Impossibile visualizzare i dati");
	    }
	});
}

function setDia3(ct)
{
	var chartType = ct;
	if(!ct)
		chartType = 'bar3d';
	
	$.ajax({
	    url: "viewProfileDiagramsSecond",
	    type: "POST",
	    async: true,
	    success: function(data)
	    {
	    	const chartData = data;
	
	        const chartConfig = {
	        type: chartType,
	        renderAt: 'chart-container3',
	        width: '100%',
	        height: '400',
	        dataFormat: 'json',
	        dataSource: {
	        	
	            "chart": {
	            	"caption": "Vendite Inserzioni",
	                "xAxisName": "Inserzione",
	                "yAxisName": "Numero di copie vendute",
	                "theme": "fusion"
	                },
	           
	            "data": chartData
	            }
	        };
	       
	        FusionCharts.ready(function(){
	        var fusioncharts = new FusionCharts(chartConfig);
	        fusioncharts.render();
	        });
	        
	    },
	    error: function()
	    {
	        alert("Impossibile visualizzare i dati");
	    }
	});
}

function setDia4(ct)
{
	var chartType = ct;
	if(!ct)
		chartType = 'line';
	
	$.ajax({
	    url: "viewProfileDiagramsSecond",
	    type: "GET",
	    async: true,
	    success: function(data)
	    {
	    	const chartData = data;
	
	        const chartConfig = {
	        type: chartType,
	        renderAt: 'chart-container4',
	        width: '100%',
	        height: '400',
	        dataFormat: 'json',
	        dataSource: {
	        	
	            "chart": {
	            	"caption": "Guadagno Inserzioni",
	                "xAxisName": "Inserzione",
	                "yAxisName": "Prezzo",
	                "lineThickness": "2",
	                "numberPrefix": "€",
	                "theme": "fusion"
	                },
	           
	            "data": chartData
	            }
	        };
	       
	        FusionCharts.ready(function(){
	        var fusioncharts = new FusionCharts(chartConfig);
	        fusioncharts.render();
	        });
	        
	    },
	    error: function()
	    {
	        alert("Impossibile visualizzare i dati");
	    }
	});
}

function setSummaryDia(ct)
{
	var chartType = ct;
	if(!ct)
		chartType = 'column3d';
	
	$.ajax({
	    url: "ViewSummaryProfile",
	    type: "POST",
	    async: true,
	    success: function(data)
	    {
	    	const chartData = data;
	
	        const chartConfig = {
	        type: chartType,
	        renderAt: 'chart-container2',
	        width: '100%',
	        height: '400',
	        dataFormat: 'json',
	        dataSource: {
	        	
	            "chart": {
	            	"caption": "Valutazioni Inserzioni",
	                "xAxisName": "Inserzione",
	                "yAxisName": "Valutazione",
	                "theme": "fusion"
	                },
	           
	            "data": chartData
	            }
	        };
	       
	        FusionCharts.ready(function(){
	        var fusioncharts = new FusionCharts(chartConfig);
	        fusioncharts.render();
	        });
	        
	    },
	    error: function()
	    {
	        alert("Impossibile visualizzare i dati");
	    }
	});
}
