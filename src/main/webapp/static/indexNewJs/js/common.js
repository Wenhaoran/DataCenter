$(document).ready(function () {
    $('.con .right').height($('.con .left').height())
    $(".introduce div").eq(0).show()
    for (var i = 0; i < $(".conBt div").length; i++) {
        $(".conBt div").eq(i).hover(function () {
            var $index = $(".conBt div").index(this)
            $(".introduce div").eq($index).show(500).siblings().hide()
        }, function () {
        	var $index = $(".conBt div").index(this)
            $(".introduce div").eq($index).show()
        });
    };

})