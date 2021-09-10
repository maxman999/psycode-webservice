var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function(){
            _this.save();
        });
    },
    scrap : function () {
        window.location.href = '/';
    },
    portfolio : function() {
    }
};

main.init();