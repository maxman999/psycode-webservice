var main = {
    init : function () {
        var _this = this;
            _this.show();
        $('#ol-news').on('click','.btn-scrap',function(){
            _this.save($(this));
        });
    },
    show : function () {
        let keyObj = {
       			newsKeyword : "인공지능"
       		};
       	$.ajax({
       		type : 'get',
       		url : '/getNews',
       		contentType : "application/json; charset=utf-8",
       		data : keyObj,
       		success : function(result) {
       			let news = JSON.parse(result).items;
       			let str = "";
       			for (var i = 0; i < news.length; i++) {
       			    let pubdate = new Date(news[i].pubDate).toISOString().substring(0,10);
       				str += "<div><li id='news-li' class='list-group-item d-flex justify-content-between align-items-start list-group-item-action mb-1'>";
       				str += "<div class='ms-2 me-auto'>";
       				str += "<div class='title fw-bold mb-1'>" + news[i].title + "</div>";
                    str += "<div class='description div-desc'>" + news[i].description + "</div>";
                    str += "<div class='pubdate'>" + pubdate + "</div>";
                    str += "<div class='originallink'>"+news[i].originallink+"</div>"
                    str += "</div>";
       				str += "<div class=div-news-btn><button class='btn btn-sm btn-outline-secondary' onclick=location.href='" + news[i].originallink + "'><i class='far fa-eye'></i></button></div>";
                    str += "<div class=div-news-btn><button class='btn btn-sm btn-outline-secondary btn-scrap'><i class='fas fa-pencil-alt'></i></button></div>";
       				str += "</li></div>";
       			}
       			$("#ol-news").html(str);
       		},
       		error : function(e) {
       			alert("통신 실패");
       		}
       	})
    },
    save : function (target){
        let newsNode = target.parent().prev().prev();
        var data = {
                    title : newsNode.find(".title").text(),
                    originallink : newsNode.find(".originallink").text(),
                    author : userEmail,
                    description : newsNode.find(".description").text(),
                    pubdate : newsNode.find(".pubdate").text()
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
            window.location.href = '/posts/read';
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },
}
main.init();