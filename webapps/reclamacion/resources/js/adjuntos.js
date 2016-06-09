var app = {
	initialize : function() {
		this.bindEvents();
		this.inicializaDiccionarios();
		this.casosUso();
		this.inicializaParametrosUrl();

	},
	ajaxStart : function() {
		$("body").addClass("loading");
	},
	ajaxStop : function() {
		$("body").removeClass("loading");
	},
	inicializaParametrosUrl : function() {
		var sPageURL = window.location.search.substring(1);
		var sURLVariables = sPageURL.split('&');
		var sParameterName = "";

		// $("#formAdjuntos").attr("action");

		var userLang = navigator.language || navigator.userLanguage;
		var idLang = userLang.split('-')[0];
		// console.log("El codigo Locale de la pagina es :: "+idLang);
		if (idLang != 'es' && idLang != 'ca')
			idLang = 'en';
		this.currentLang = idLang;

		for ( var i = 0; i < sURLVariables.length; i++) {
			sParameterName = sURLVariables[i].split('=');
			if (sParameterName[0] == 'caso')
				app.currentCaso = sParameterName[1];

			if (sParameterName[0] == 'codigoReclamacion') {
				var url = "http://localhost:8080/dfpas2/jsonServlet?caso=2&codigoReclamacion="
						+ sParameterName[1];
				// var url =
				// "http://wscentral-dfpas.rhcloud.com/jsonServlet?caso=2&codigoReclamacion="+sParameterName[1];
				$("#formAdjuntos").attr("action", url);
				// $("#codigoReclamacion").val(sParameterName[1]);
			}
			// if(sParameterName[0]=='language')
			// app.currentLang = sParameterName[1];
			console.log(sParameterName[1]);
		}
	},
	traduceCadena : function(id) {
		var idLang = this.currentLang;
		return this.aLangKeys['' + idLang]['' + id];
	},
	currentCaso : "",
	currentLang : "",
	aLangKeys : new Array(),
	inicializaDiccionarios : function() {
		var userLang = navigator.language || navigator.userLanguage;
		var idLang = userLang.split('-')[0];
		console.log("El codigo Locale de la pagina es :: " + idLang);

		if (idLang != 'es' && idLang != 'ca')
			idLang = 'en';

		this.currentLang = idLang;

		this.aLangKeys['es'] = new Array();
		this.aLangKeys['en'] = new Array();
		this.aLangKeys['ca'] = new Array();

		this.aLangKeys['es']['emailError'] = 'El email no es correcto';
		this.aLangKeys['es']['campoObligatorio'] = 'Este campo es obligatorio';
		this.aLangKeys['es']['adjuntaDocGastos'] = 'Adjunta documento gasto (Formato: pdf, png, jpg, gif, word. Tamaño máximo:';
		this.aLangKeys['es']['campoSoloLetras'] = 'Este campo solo admite letras';
		this.aLangKeys['es']['cancelacionVuelo'] = 'Reclamación por Cancelación de Vuelo';
		this.aLangKeys['es']['retrasoVuelo'] = 'Reclamación por Retraso de Vuelo';
		this.aLangKeys['es']['denegaembarque'] = 'Reclamación por Overbooking o Denegación de Embarque';
		this.aLangKeys['es']['danoEquipaje'] = 'Reclamación por Pérdida o Daño de Equipaje';
		this.aLangKeys['es']['errorServidor'] = 'El servidor, no está disponible vuelva intentarlo pasados unos minutos. O pongase en contacto con nosotros en el teléfono  984.04.12.75';

		this.aLangKeys['en']['emailError'] = 'Email is not correct';
		this.aLangKeys['en']['campoObligatorio'] = 'This field is required';
		this.aLangKeys['en']['adjuntaDocGastos'] = 'Attached document expenditure (Format: pdf, png, jpg, gif, word maximum size.:';
		this.aLangKeys['en']['campoSoloLetras'] = 'This field only accepts letters';
		this.aLangKeys['en']['cancelacionVuelo'] = 'Flight Cancellation claim';
		this.aLangKeys['en']['retrasoVuelo'] = 'Claim For Flight Delay';
		this.aLangKeys['en']['denegaembarque'] = 'Overbooking claim or Denied Boarding';
		this.aLangKeys['en']['danoEquipaje'] = 'Claims for Loss or Damage Baggage';
		this.aLangKeys['en']['errorServidor'] = 'The server is not available try again after a few minutes. Or contact us on the phone 984.04.12.75';

		this.aLangKeys['ca']['emailError'] = 'El correu electrònic no és correcte';
		this.aLangKeys['ca']['campoObligatorio'] = 'Aquest camp és obligatori';
		this.aLangKeys['ca']['adjuntaDocGastos'] = 'Adjunta document despesa (Format: pdf, png, jpg, gif, word. Mida màxima:';
		this.aLangKeys['ca']['campoSoloLetras'] = 'Aquest camp només admet lletres';
		this.aLangKeys['ca']['cancelacionVuelo'] = 'Reclamació per Cancel·lació de Vol';
		this.aLangKeys['ca']['retrasoVuelo'] = 'Reclamació per Retard de Vol';
		this.aLangKeys['ca']['denegaembarque'] = 'Reclamació per Overbooking o Denegació d\'embarcament';
		this.aLangKeys['ca']['danoEquipaje'] = 'Reclamació per Pèrdua o Dany d\'equipatge';
		this.aLangKeys['ca']['errorServidor'] = 'El servidor, no està disponible torni intentar-passats uns minuts. O poseu-vos en contacte amb nosaltres al telèfon 984.04.12.75';

		// https://www.script-tutorials.com/how-to-translate-your-site-in-runtime-using-jquery/

	},
	validaDocAdjuntos : function() {
		var ok = true;
		// validaciones campo vacio
		if (!app.validaVacio('#adjunta-billete'))
			ok = false;
		else if (!app.validaSize('adjunta-billete'))
			ok = false;

		if (!app.validaVacio('#adjunta-dni-front'))
			ok = false;
		else if (!app.validaSize('adjunta-dni-front'))
			ok = false;

		if (!app.validaVacio('#adjunta-dni-back'))
			ok = false;
		else if (!app.validaSize('adjunta-dni-back'))
			ok = false;

//		if (!app.validaVacio('#adjunta-pir-maleta'))
//			ok = false;
//		else if (!app.validaSize('adjunta-pir-maleta'))
//			ok = false;

		if (ok == true) {
			toastr["info"]("Enviando adjuntos");
			app.ajaxStart();
		} else {
			toastr["error"]("Revisa el formulario, tiene errores");
		}
		return ok;
	},
	validaSize : function(alfabetico) {
		// resetea detalle Error
		$(alfabetico + "Error").text("");
		var input = document.getElementById(alfabetico);

		var valor = input.files[0].size;
		console.log("validaVacio>>> tamaño fichero >>>>>" + valor);
		if (valor > 1048576) {
			$("#" + alfabetico + "Error")
					.html(
							"Este fichero no puede pesar más de 1MB, si es una imagén puedes reducirlo con esta herramienta (<a href='https://compressor.io/compress' target='black'>Compressor.io</a>)");
			return false;
		}
		return true;
	},
	bindEvents : function() {
		
		var addGasto = document.getElementById('addGasto');

		if (addGasto != null) {
			addGasto.addEventListener('click', function() {
				app.addDocumentoGasto();
			}, false);
		}

		var formAdjuntos = document.getElementById('formAdjuntos');

		formAdjuntos.addEventListener('submit', function(evt) {
			ok = app.validaDocAdjuntos();
			if (ok == false) {
				evt.preventDefault();
			}
		}, false);
		
		if (app.currentCaso=='equipaje')
			$('#adjunta-pir-maleta-row').show();
		else 
			$('#adjunta-pir-maleta-row').hide();

	},
	addDocumentoGasto : function() {
		if ($(".gasto").length <= 3) {
			var htmlDocQueja = "<div class='row temporal'><div class='col-md-12 gasto'>Adjunta documento gasto (Formato: pdf, png, jpg, gif, word. Tamaño máximo: <strong>3MB</strong>)<br><span ><input type='file' name='adjunta-doc-gasto' size='40' accept='.pdf, .png, .jpg, .gif, .doc, .docx' ></span></div></div>";
			$(htmlDocQueja).insertAfter($(".docquejas"));
			$(".docquejas").removeClass("docquejas");
			$(".temporal").last().addClass("docquejas");
			$(".docquejas").removeClass("temporal");
			if ($(".gasto").length == 3)
				$("#divAddGasto").hide();
		} else {
			$("#addGasto").hide();
		}
	},
	validaMail : function() {
		var userinput = $('#email').val();
		$("#emailError").text("");
		var pattern = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;
		var result = pattern.test(userinput);

		if (!result)
			$("#emailError").text("El email no es correcto");

		return !result;
	},
	validaVacioSinMsg : function(alfabetico) {
		// resetea detalle Error
		$(alfabetico + "Error").text("");
		var valor = $(alfabetico).val();
		console.log("validaVacio>>>" + valor);
		if (valor == null || valor.length == 0 || /^\s*$/.test(valor)) {
			return false;
		}
		return true;
	},
	validaVacio : function(alfabetico) {
		// resetea detalle Error
		$(alfabetico + "Error").text("");
		var valor = $(alfabetico).val();
		console.log("validaVacio>>>" + valor);
		if (valor == null || valor.length == 0 || /^\s*$/.test(valor)) {
			$(alfabetico + "Error").text("Este campo es obligatorio");
			// alert (alfabetico+ ' no puede estar vacío o contener sólo
			// espacios en blanco');
			return false;
		}
		return true;
	},
	validaNombres : function(alfabetico) {
		$(alfabetico + "Error").text("");

		if (!/^[a-zA-ZÑñáéíóúÁÉÍÓÚäëïöüÄËÏÖÜàèìòùÀÈÌÒÙ\s]*$/g
				.test($(alfabetico).val())) {
			$(alfabetico + "Error").text("Este campo solo admite letras");
			return false;
		}
		return true;
	},
	casosUso : function() {
		var sPageURL = window.location.search.substring(1);
		var sURLVariables = sPageURL.split('&');
		var sParameterName = "";
		for ( var i = 0; i < sURLVariables.length; i++) {
			sParameterName = sURLVariables[i].split('=');
			if (sParameterName[0] == 'caso')
				app.currentCaso = sParameterName[1];
			console.log(sParameterName[1]);
		}

		if (app.currentCaso == 'cancel') {
			$('#tituloFormulario').text("Reclamación por Cancelación de Vuelo");
		}

		if (app.currentCaso == 'retraso') {
			$('#tituloFormulario').text("Reclamación por Retraso de Vuelo");
		}

		if (app.currentCaso == 'over') {
			$('#tituloFormulario').text(
					"Reclamación por Overbooking o Denegación de Embarque");
		}

		if (app.currentCaso == 'equipaje') {
			$('#tituloFormulario').text(
					"Reclamación por Pérdida o Daño de Equipaje");
		}

	}
};
