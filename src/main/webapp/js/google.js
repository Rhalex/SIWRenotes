/**
 * 
 */

function onSignIn(googleUser) 
	{
		var auth2 = gapi.auth2.getAuthInstance();
	     auth2.disconnect();
		var profile = googleUser.getBasicProfile();
   		 console.log(profile.getEmail());
    	var dati = [profile.getEmail(), profile.getName()];
		$.ajax({
			url: "GoogleLogin",
			type: "POST",
			async: true,
			contentType: "application/json",
			data: JSON.stringify(profile.getEmail()),
			success: function(data)
			{
        		window.location.replace("http://localhost:8080/it.unical.ingsw.siw.renotes/ViewAdList");
			},
			error: function()
			{
				alert("Impossibile effettuare il login");
			}
		});

		  /*var profile = googleUser.getBasicProfile();
		  console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
		  console.log('Name: ' + profile.getName());
		  console.log('Image URL: ' + profile.getImageUrl());
		  console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
		  */
	}
	
	  function signOut() {
	    var auth2 = gapi.auth2.getAuthInstance();
	    auth2.signOut().then(function () {
	      console.log('User signed out.');
	    });
	  }