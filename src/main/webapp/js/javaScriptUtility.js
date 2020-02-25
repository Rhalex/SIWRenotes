/**
 * 
 */

function calculateParameters(q,a,c)
{
	var element = document.getElementById("statistics");
	
	var list = [q, a, c];
	for(var i=0; i<list.length; i++)
	{
		element.innerHTML += "<p>";
		for(var j=0; j<list[i]; j++)
			element.innerHTML += "<i class=\"fas fa-star text-warning\"></i>";
		
		for(var k=0; k<5-list[i]; k++)
			element.innerHTML += "<i class=\"fas fa-star text-black-50\"></i>";
		
		element.innerHTML += "</p>"; 	
	}
	
}

function filterAds(ads)
{
	$.ajax({
	    url: "ViewAdList",
	    type: "GET",
	    async: true,
	    success: function(data)
	    {
	    	//var adsList = JSON.parse(data);
	    	if(data == 'd')
	    	{
	    		window.location.replace("http://localhost:8080/it.unical.ingsw.siw.renotes/adListNUOVO.jsp");
	    	}
	    
	    },
	 	error: function()
	    {
	        alert("Impossibile visualizzare i dati");
	    }
	});
}

function checkBundleTitle()
{
	 var x = document.getElementById("titoloBundle").value;
	 var patt = /\S+/g;
	 var result = patt.exec(x);
	
	 if(x.length>0 && result == null)
	 {
		 document.getElementById("resp").innerHTML = "Il campo TITOLO non può essere uno spazio vuoto";
		 $("#bottone").attr("disabled", 'disabled'); 
	 } 
	 else
	 {	 
		 document.getElementById("resp").innerHTML = null; 
		 document.getElementById("bottone").removeAttribute("disabled");
	 }
		 
}

function checkBundleItems(max)
{
	var cont = 0;
	for(var i=0; i<max; i++)
	{
		var index = i+1;
		var sel = "#"+index+"";
		
		if($(sel).is(":checked"))
			cont++;
	}
	
	if(cont>=2 && cont<=5)
	{
		
		document.create.action = "CreateBundle";
		 $("#form").attr("method", 'post');
		document.create.submit();
	}
	else
	{
		alert("Bisogna scegliere almeno 2 bundle per proseguire");
		$("#form").removeAttribute("method");
	}
			
}

function checkModifyPwd()
{
	 var x = document.getElementById("password").value;
	 var patt = /\S+/g;
	 var result = patt.exec(x);
	
	 if(x.length>0 && result == null)
	 {
		 document.getElementById("resp").innerHTML = "La tua password non può essere una stringa vuota";
		 $("#applicaModifica").attr("disabled", 'disabled'); 
	 }
	 else if(x.length==0)
	 {
		 document.getElementById("resp").innerHTML = null; 
		 $("#applicaModifica").attr("disabled", 'disabled'); 
	 }
	 else
	 {
		 document.getElementById("resp").innerHTML = null; 
		 document.getElementById("applicaModifica").removeAttribute("disabled");
	 }
}

//function autocomplete()
//{
	//alert("qui");
/*	
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
		    renderItem: function (item, search){
				
		    	if(document.getElementById("suggestion"))
		    		{document.getElementById("suggestion").remove()};
		    		
		    	var t = document.createElement("a");
		    	t.id = "suggestion";
		    	t.className += "dropdown-content";
		    	t.append(item);
		    	document.getElementById("dropdown-menu").append(t);
		    	return '';
		    	
		    }
		});

  		

$(document).on('click', "[id^='suggestion']", function () {
	document.getElementById("aTitolo").value = document.getElementById("suggestion").textContent;
	document.getElementById("suggestion").remove();
});*/
//}