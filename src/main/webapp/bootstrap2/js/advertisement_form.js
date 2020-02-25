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
    
   // var myUploadedFile = document.getElementById("myFile").files[0];
   // var src = window.URL.createObjectURL(myUploadedFile);
   // var a = document.getElementById("prova");
   //a.setAttribute('href', src);
    
   // var txt = document.getElementById("url");
   // txt.innerHTML += src;
    //window.URL.revokeObjectURL(src);
    //alert(src);
});