/**
 * 
 */

function auto(selector, menu, sugg)
{

	var sel = "#"+selector+"";
	$( sel ).keyup(function() {
	 
			var value = $.value;
			if (value==null){
				if(document.getElementById(sugg))
	    		{document.getElementById(sugg).remove()};
	    		return '';
			};
		});
			var xhr;
			var suggestValue;
			$(sel).autoComplete({
				minChars: 1,
				cache: false,
			    source: function(term, response){
			    	console.log(term);
			    	var t = { titolo : term };
			    	var s = { materia : term};
			    	var u = { universita : term};
			    	var d = { corsoDiLaurea : term};
			    	
			    	var value;
			    	switch(selector)
			    	{
			    		case "aTitolo":
			    			value = t;
			    			break;
			    		
			    		case "m":
			    			value = s;
			    			break;
			    			
			    		case "u":
			    			value = u;
			    			break;
			    		
			    		case "c":
			    			value = d;
			    			break;
			    	}
			    	
			        xhr = $.getJSON('Autocomplete', value , function(data){ response(data); });
			        suggestValue = $.textContent; 
			       	if(suggestValue == null){
			       		if(document.getElementById(sugg))
			       		{document.getElementById(sugg).remove()};
			       		return '';
			       	}
			    },
			    _renderMenu: function( ul, items ) {
			    	  var that = this;
			    	  $.each( items, function( index, item ) {
			    	    that._renderItemData( ul, item );
			    	  });
			    }, 
			     renderItem: function (item, search){
					
			    	if(document.getElementById(sugg))
			    		{document.getElementById(sugg).remove()};
			    		
			    	var t = document.createElement("option");
			    	t.id = sugg;
			    	t.className += "dropdown-content";
			    	t.append(item);
			    	document.getElementById(menu).append(t);
			    	return '';
			    	
			    }
			});
			
			var s = "#"+sugg+"";
			$(document).on('click', s, function () {
				document.getElementById(selector).value = document.getElementById(sugg).textContent;
				document.getElementById(sugg).remove();
				$(sel).focus();
			});
}