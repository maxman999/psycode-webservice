var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function(){
            _this.save();
        });
        $('#btn-update').on('click', function(){
            _this.update();
        });
        $('.btn-delete').on('click', function(){
            _this.delete($(this));
        });
    },
    save : function () {
        var data = {
            title : $('#title').val(),
            originallink : $('#originallink').val(),
            author : $('#author').val(),
            description : $('#description').val(),
            pubdate : $('#pubdate').val(),
            summary : $('#summary').val(),
        };
        console.log(data);
        $.ajax({
            type : 'POST',
            url : '/api/v1/posts',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function(){
            alert('글이 등록되었습니다.');
            window.location.href = '/posts/read?page=1';
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var data = {
            title : $('#title').val(),
            content : $('#content').val()
        };
        var id = $('#id').val();
        $.ajax({
            type : 'PUT',
            url : '/api/v1/posts/'+id,
            dataType : 'json',
            contentType : 'application/json; charset = utf-8',
            data : JSON.stringify(data)
        }).done(function(){
            alert('글이 수정되었습니다.');
            window.location.href = '/posts/read?page='+page;
        }).fail(function(error){
            alert(JSON.stringify(error))
        });
    },
    delete : function (target){
        var id = target.data(id).id;
        $.ajax({
            type : 'DELETE',
            url : '/api/v1/posts/' + id,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8'
        }).done(function(){
            alert('글이 삭제되었습니다.');
            window.location.reload();
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },
};

main.init();