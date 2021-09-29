var main = {
    init : function () {
        let _this = this;
        $(".setting-btn").click(function(){
            _this.kewordSave();
        })

    },
    kewordSave : function () {
        let data = {
            keyword1_user : $('#keyword1').val(),
            keyword2_user : $('#keyword2').val(),
            keyword3_user : $('#keyword3').val(),
            useremail : $('#email').val(),
        };
        console.log(data);
        $.ajax({
            type : 'POST',
            url : '/api/v1/keywords',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function(){
            alert('수정되었습니다.');
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    }
};

main.init();
