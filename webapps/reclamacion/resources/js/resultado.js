var app = {
    initialize: function() {
        this.bindEvents();
      //  this.inicializaDiccionarios();
        this.casosUso();
        this.inicializaParametrosUrl();
    },
    inicializaParametrosUrl:function(){
      var sPageURL = window.location.search.substring(1);
      var sURLVariables = sPageURL.split('&');
      var sParameterName = "";
      for (var i = 0; i < sURLVariables.length; i++)
      {
          sParameterName = sURLVariables[i].split('=');
          if (sParameterName=='resultEnvio')
            app.currentCaso = sParameterName[1];
          console.log(sParameterName[1]);
      }
    }
    ,
    traduceCadena:function(id){
      var idLang = this.currentLang;
      return this.aLangKeys[''+idLang][''+id];
    }
    ,
    currentResult:""
    ,
    currentLang:""
    ,
    aLangKeys:new Array()
    ,
    inicializaDiccionarios: function() {
      var userLang = navigator.language || navigator.userLanguage;
      var idLang = userLang.split('-')[0];
      console.log("El codigo Locale de la pagina es :: "+idLang);

      if (idLang!='es' && idLang!='ca')
        idLang = 'en';

      this.currentLang = idLang;

      this.aLangKeys['es']=new Array();
      this.aLangKeys['en']=new Array();
      this.aLangKeys['ca']=new Array();

      this.aLangKeys['es']['emailError']='El email no es correcto';
      this.aLangKeys['es']['campoObligatorio']='Este campo es obligatorio';
      this.aLangKeys['es']['adjuntaDocGastos']='Adjunta documento gasto (Formato: pdf, png, jpg, gif, word. Tamaño máximo:';
      this.aLangKeys['es']['campoSoloLetras']='Este campo solo admite letras';
      this.aLangKeys['es']['cancelacionVuelo']='Reclamación por Cancelación de Vuelo';
      this.aLangKeys['es']['retrasoVuelo']='Reclamación por Retraso de Vuelo';
      this.aLangKeys['es']['denegaembarque']='Reclamación por Overbooking o Denegación de Embarque';
      this.aLangKeys['es']['danoEquipaje']='Reclamación por Pérdida o Daño de Equipaje';
      this.aLangKeys['es']['errorServidor']='El servidor, no está disponible vuelva intentarlo pasados unos minutos. O pongase en contacto con nosotros en el teléfono  984.04.12.75';

      this.aLangKeys['en']['emailError']='Email is not correct';
      this.aLangKeys['en']['campoObligatorio']='This field is required';
      this.aLangKeys['en']['adjuntaDocGastos']='Attached document expenditure (Format: pdf, png, jpg, gif, word maximum size.:';
      this.aLangKeys['en']['campoSoloLetras']='This field only accepts letters';
      this.aLangKeys['en']['cancelacionVuelo']='Flight Cancellation claim';
      this.aLangKeys['en']['retrasoVuelo']='Claim For Flight Delay';
      this.aLangKeys['en']['denegaembarque']='Overbooking claim or Denied Boarding';
      this.aLangKeys['en']['danoEquipaje']='Claims for Loss or Damage Baggage';
      this.aLangKeys['en']['errorServidor']='The server is not available try again after a few minutes. Or contact us on the phone 984.04.12.75';

      this.aLangKeys['ca']['emailError']='El correu electrònic no és correcte';
      this.aLangKeys['ca']['campoObligatorio']='Aquest camp és obligatori';
      this.aLangKeys['ca']['adjuntaDocGastos']='Adjunta document despesa (Format: pdf, png, jpg, gif, word. Mida màxima:';
      this.aLangKeys['ca']['campoSoloLetras']='Aquest camp només admet lletres';
      this.aLangKeys['ca']['cancelacionVuelo']='Reclamació per Cancel·lació de Vol';
      this.aLangKeys['ca']['retrasoVuelo']='Reclamació per Retard de Vol';
      this.aLangKeys['ca']['denegaembarque']='Reclamació per Overbooking o Denegació d\'embarcament';
      this.aLangKeys['ca']['danoEquipaje']='Reclamació per Pèrdua o Dany d\'equipatge';
      this.aLangKeys['ca']['errorServidor']='El servidor, no està disponible torni intentar-passats uns minuts. O poseu-vos en contacte amb nosaltres al telèfon 984.04.12.75';

      //https://www.script-tutorials.com/how-to-translate-your-site-in-runtime-using-jquery/

    }
    ,
    bindEvents: function() {
      var volverPagina = document.getElementById('volverPagina');

      if (volverPagina!=null){
        volverPagina.addEventListener('click',function(){
          window.location.href = "/..";
        },false);
      }

//      var formAdjuntos = document.getElementById('formAdjuntos');
//
//      formAdjuntos.addEventListener('submit',function(evt){
//        ok = app.validaDocAdjuntos();
//        if (ok==false){
//           evt.preventDefault();
//        }
//      },false);



    },
    casosUso: function() {
      var sPageURL = window.location.search.substring(1);
      var sURLVariables = sPageURL.split('&');
      var sParameterName = "";
      for (var i = 0; i < sURLVariables.length; i++)
      {
          sParameterName = sURLVariables[i].split('=');
          if(sParameterName[0]=='resultEnvio')
            app.currentResult = sParameterName[1];
          console.log(sParameterName[1]);
      }

      if(app.currentResult=='true'){
        $('#msg-final-error').hide();
        $('#msg-final-exito').show();
      }

      if(app.currentResult=='false'){
        $('#msg-final-exito').hide();
        $('#msg-final-error').show();
      }

    }
};
