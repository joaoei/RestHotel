
function main() {

(function () {
   'use strict';
   
  	$('a.page-scroll').click(function() {
        if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'') && location.hostname == this.hostname) {
          var target = $(this.hash);
          target = target.length ? target : $('[name=' + this.hash.slice(1) +']');
          if (target.length) {
            $('html,body').animate({
              scrollTop: target.offset().top - 40
            }, 900);
            return false;
          }
        }
      });

	
    // Show Menu on Book
    $(window).bind('scroll', function() {
        var navHeight = $(window).height() - 500;
        if ($(window).scrollTop() > navHeight) {
            $('.navbar-default').addClass('on');
        } else {
            $('.navbar-default').removeClass('on');
        }
    });

    $('body').scrollspy({ 
        target: '.navbar-default',
        offset: 80
    });

	// Hide nav on click
  $(".navbar-nav li a").click(function (event) {
    // check if window is small enough so dropdown is created
    var toggle = $(".navbar-toggle").is(":visible");
    if (toggle) {
      $(".navbar-collapse").collapse('hide');
    }
  });
	
  	// Portfolio isotope filter
    $(window).load(function() {
        var $container = $('.portfolio-items');
        $container.isotope({
            filter: '*',
            animationOptions: {
                duration: 750,
                easing: 'linear',
                queue: false
            }
        });
        $('.cat a').click(function() {
            $('.cat .active').removeClass('active');
            $(this).addClass('active');
            var selector = $(this).attr('data-filter');
            $container.isotope({
                filter: selector,
                animationOptions: {
                    duration: 750,
                    easing: 'linear',
                    queue: false
                }
            });
            return false;
        });

    });
	
    // Nivo Lightbox 
    $('.portfolio-item a').nivoLightbox({
            effect: 'slideDown',  
            keyboardNav: true,                            
        });

}());


}

function getQuartos() {

    var ajax = new XMLHttpRequest();

    ajax.onreadystatechange = function() {
        $('#row_tipo_1').css("display", "none");
        $('#row_tipo_2').css("display", "none");

        if (ajax.readyState == 4 && ajax.status == 200) {
            try {
                var data = JSON.parse(ajax.responseText);
                if (data.status) {
                    let tipos = {};

                    // Varrer array e salvar a qntd por tipo e o preço
                    data.resposta.forEach(function (quarto) {
                        if (typeof tipos["tipo_"+quarto.tipo] === "undefined") {
                            tipos["tipo_"+quarto.tipo] = {
                                tipo: quarto.tipo,
                                preco: quarto.preco_diaria,
                                num_vagas: 1
                            };
                        } else {
                            tipos["tipo_"+quarto.tipo].num_vagas++;
                        }
                    });

                    // Depois adicionar na tela
                    for (var key in tipos) {
                        let div = $('#tipo_'+tipos[key].tipo);
                        div.html(`
                            <H4>TIPO ${tipos[key].tipo}</H4>
                            <h4>PREÇO DA DIÁRIA: R$ ${tipos[key].preco}</h4>
                            <h4>QUANTIDADE DE QUARTOS DISPONIVEIS: ${tipos[key].num_vagas}</h4>
                        `);

                        $('#row_tipo_'+tipos[key].tipo).css("display", "block");
                    }
                }
            } catch (e) {
                console.log('erro', e.message);
            }
        }
    }

    ajax.open("GET", "http://localhost:8080/Hotel_rest_war_exploded/reserva/listar_quartos", true);
    ajax.send();
}

getQuartos();
main();