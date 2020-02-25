$(document).ready(function() {

    
    var readURL = function(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('.avatar').attr('src', e.target.result);
            }
    
            reader.readAsDataURL(input.files[0]);
        }
    }
    

    $(".file-upload").on('change', function(){
        readURL(this);
    });
});

$.ajax({
    url: "ViewProfileServlet",
    type: "POST",
    async: true,
    success: function(data)
    {
    	
    	if(data[0] == 'false')
    		window.location.replace("http://localhost:8080/it.unical.ingsw.siw.renotes/notLogin.html");
    	
    	else
    	{
	        document.getElementById("username-input").setAttribute("placeholder", data[0]);
	        document.getElementById("email-input").setAttribute("placeholder", data[1]);
	        document.getElementById("rate").setAttribute("value", data[2]);
    	}
        //caricaDati(data);
    },
    error: function()
    {
        alert("Impossibile visualizzare i dati");
    }
});

function caricaDati(data)
{
    $("#username-input").text(data[0]);
    $("#email-input").text(data[1]);
}
