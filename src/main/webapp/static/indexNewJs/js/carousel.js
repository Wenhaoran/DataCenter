$(document).ready(function () {
    $('#box>div').eq(0).show()
    var $index = 0;
    var timer;
    var btns = $('#btn>li');
    btns.eq(0).css('opacity', '1')
    timer = setInterval(fade, 2000)

    function fade() {
        $index++;
        if ($index == 3) {
            $index = 0;
        }
        $('#box>div').eq($index).stop().fadeIn(1000, function () {
            clearInterval(timer);
            timer = setInterval(fade, 2000);
        }).siblings('div').stop().fadeOut(1000)
        btns.eq($index).stop().fadeTo(1000, 1).siblings('li').fadeTo(1000, 0.3)
    }
    var btns = $('#btn>li');
    btns.click(function () {
        clearInterval(timer);
        $(this).css('opacity', '1').siblings('li').css('opacity', '0.3')
        $index = $(this).index('#btn>li');
        $('#box>div').eq($index).stop().fadeIn(1000).siblings('div').stop().fadeOut(1000)
        timer = setInterval(fade, 2000)
    })

})