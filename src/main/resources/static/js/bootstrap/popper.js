<!-- 툴팁 & 팝오버 설정 -->
$(function(){
    $("[data-bs-toggle=popover]").popover({
        html: true,
        trigger : 'focus',
        content: function() {
        return $('#popover-content').html();}
    });

    $('.tooltips').tooltip({
        animation: true,
        delay: {show:50, hide:10},
        trigger: 'hover focus',
        viewport: { selector: 'body', padding: 0 }
    });
});

$('.tooltips').on("click", function(){
    $('.tooltips').tooltip('hide');
});