<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modifica Inserzione</title>

	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link href="https://netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="bootstrap2/css/advertisement_form.css">
	
</head>
<body>
<div class="container bootstrap snippet">
    <div class="row">
      <div class="col-sm-10"><h1><strong>Modifica Inserzione</strong></h1></div>
     <form action="ViewAdList" method="post">
        	<input type="image" src= "bootstrap2/resources/icon1_black.png" width="60" height="60" class="pull-right">
        </form>
    </div>
	
	<hr>
    <div class="row">
    
    <c:if test="${ad == null }">
    	<p class="my-5 mx-5 h5"> OPS!! NESSUNA INSERZIONE DA VISUALIZZARE, RIPROVA </p>	
    </c:if>
    
    <c:if test="${ad != null }">
  		<div class="col-sm-3"><!--left col-->
              
			
      <div class="text-center">
        <img src="${ad.getPreview().getImage()}" class="avatar img-square img-thumbnail" alt="avatar">
        <h5>Scegli un'anteprima per l'inserzione</h5>
       
        <input type="file" accept="image/*" class="text-center center-block file-upload" name="Anteprima" form="load_advertisement" required>
      </div></hr><br>

         <hr>
		
		<h4>Carica i tuoi appunti</h4>
        	<input type="file" accept="video/*,.pdf"  class="text-center center-block file-upload" name="File" id="myFile" form="load_advertisement" required>
          
		<hr>
		
        </div><!--/col-3-->
    	<div class="col-sm-0">
		
              
          <div class="tab-content">
            <div class="tab-pane active" id="home">
                
                  <form class="form" action="ModifyAdvertisement" method="post" id="load_advertisement">
                  	<textarea hidden name="adId">${ad.getId()}</textarea>
                  	<textarea hidden name="previewId">${ad.getPreview().getId()}</textarea>
                     
                      <div class="form-group">
                
                          <div class="col-xs-6">
                              <label for="Titolo"><h4>Titolo*</h4></label>
                              <input type="text" class="form-control" name="Titolo" id="titolo" placeholder="Inserisci un titolo" value="${ad.getTitle() }" required>
                          </div>
                      </div>
					  
                      <div class="form-group">
                          
                          <div class="col-xs-6">
                            <label for="Materia"><h4>Materia*</h4></label>
                              <input type="text" class="form-control" name="Materia" id="materia" placeholder="Inserisci la materia di appartenenza" value="${ad.getSubject() }" required>
                          </div>
                      </div>
          
                  	  <div class="form-group">
                          
                          <div class="col-xs-6">
                            <label for="CdL"><h4>CdL</h4></label>
                              <input type="text" class="form-control" name="CdL" id="cdl" placeholder="Corso di laurea" value="${ad.getDegreeCourse() }">
                          </div>
                      </div> 
          
					  <div class="form-group">
                          
                          <div class="col-xs-6">
                            <label for="Universita"><h4>Università </h4></label>
                              <input type="text" class="form-control" name="Universita" id="universita" placeholder="Inserisci l'università  relativa al CdL" value="${ad.getUniversity() }">
                          </div>
                      </div>
          

					  <div class="form-group">
                          
                          <div class="col-xs-6">
                            <label for="descrizione"><h4>Descrizione*</h4></label>
                              <textarea rows="10" cols="50" type="text" maxlength="1000" class="form-control" id="description-resize-off" name="Descrizione"  placeholder="Inserisci una descrizione di massimo 1000 caratteri" required>${ad.getDescription() }</textarea>
                          </div>
                      </div>
          
					  <div class="form-group">
                          
                          <div class="col-xs-2">
                            <label for="Prezzo"><h4>Prezzo*</h4></label>
                              <input type="number" min="0" step="0.01" class="form-control" name="Prezzo" id="prezzo" placeholder="€" value="${ad.getPrice() }" required>
                          </div>
                      </div>
					  
					   <div class="col-xs-2">
					   		 <label ><h4>Versione*</h4></label><br>
					   		<div class="info col-lg-12">
							      <i class="icon-info-sign"></i>
							
							      <span class="extra-info text-white">
							       Se modifichi il file la modifica è da considerarsi sostanziale. Negli altri casi è leggera
							      </span>
							  </div>
							 <br><br>
                           
                            <textarea hidden name="versione">${ad.getVersion()}</textarea>
                         	<span> <input type="radio" name="sceltaVersione" value="1" required /> Modifica sostanziale </span><br>
							<span> <input type="radio" name="sceltaVersione" value="2" /> Modifica leggera </span>
                         </div>
                      </div>
                      
                      <div class="form-group">
                           <div class="col-xs-12">
                                <br>
                              	<button class="btn btn-lg btn-success pull-right" type="submit"><i class="glyphicon glyphicon-ok-sign"></i>Applica</button>
                           </div>
                      </div>
					  
					  
              	</form>
              
			  <div class=col-sm-12><h4>* I campi sono obbligatori.</h4></div>
              
              
             </div><!--/tab-pane-->
             
          </div><!--/tab-content-->

        </div><!--/col-9-->
        </c:if>
    </div><!--/row-->   
</body>

<script type="text/JavaScript" src="bootstrap2/js/advertisement_form.js"></script>
</body>
</html>