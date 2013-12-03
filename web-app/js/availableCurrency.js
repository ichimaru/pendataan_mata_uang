    // load domain
    $(function(){
      // set onblur event handler
      $("#sym").blur(function(){
        // jika ada simbol yg diinput ini == #sym akan dicek
        if($(this).length > 0) {
          // pakai create link agar tidak menghardcode
          var url = "${createLink(controller:'currency', action:'checkAvailable')}"
          // async ajax request pass username entered as id parameter
          $.getJSON(url, {id:$(this).val()}, function(json){
            if(!json.available) {
              // highlight field so user knows there's a problem
              $("#sym").css("border", "1px solid red");
              // maybe throw up an alert box
              alert("Mata Uang Dengan Simbol Tersebut Sudah Ada");
              // populate a hidden div on page and fill and display it..
              $("#somehiddendiv").html("Duplikasi Simbol").show();
            }
          });
        }
      });
    });
