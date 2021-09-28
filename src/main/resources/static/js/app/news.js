var main = {
    init : function () {
        let _this = this;
        _this.show();

        $('.ol-news').on('click','.btn-scrap',function(){
            let targetDiv = $(this).parent().parent().prev();
            let isDuple = _this.check(targetDiv);
            if(isDuple === false){
                alert("이미 등록된 기사입니다.");
            }else if(isDuple === true){
                _this.save(targetDiv);
            }
        });

        $("#keyword-search").on('click', function(){
            let keyword = $('#keyword-input').val();
            _this.show(keyword);
        })

        $(".btn-criteria").on('click', function(){
            let keyword = _this.criteria($(this).attr("id"));
            _this.show(keyword);
        })

    },
    show : function(inputkey) {
        var newsKeyword;
        if(inputkey == null){
            newsKeyword = "인공지능";
        }else{
            newsKeyword = inputkey;
        }
        let keyObj = {
       			newsKeyword : newsKeyword,
       		};
       	$.ajax({
       		type : 'get',
       		url : '/getNews',
       		contentType : "application/json; charset=utf-8",
       		data : keyObj,
       		success : function(result) {
       			let news = JSON.parse(result).items;
       			let str = "";
       			let strArr = new Array();
       			for (var i = 0; i < news.length; i++) {
       			    let pubdate = new Date(news[i].pubDate).toISOString().substring(0,10);
       				str += "<div><li class='list-group-item d-flex justify-content-between align-items-start list-group-item-action mb-2 shadow-sm rounded'>";
       				str += "<h6><span class='position-absolute top-5 start-0 translate-middle badge rounded-pill bg-dark'>"+ (i+1) +"<span class='visually-hidden'>news count</span></span></h6>"
       				str += "<div class='ms-2 me-auto'>";
       				str += "<div class='title fw-bold mb-1'>" + news[i].title + "</div>";
                    str += "<div class='description div-desc'>" + news[i].description + "</div>";
                    str += "<div class='pubdate' style='display: none'>" + pubdate + "</div>";
                    str += "<div class='originallink' style='display: none'>"+news[i].originallink+"</div>"
                    str += "</div><div class='col'>";
       				str += "<div><button class='btn btn-sm btn-outline-secondary news-btn' onclick=location.href='" + news[i].originallink + "'><i class='far fa-eye'></i> view </button></div>";
                    str += "<div><button class='btn btn-sm btn-outline-secondary news-btn btn-scrap'><i class='fas fa-pencil-alt'></i> scrap </button></div>";
       				str += "</li></div></div>";
       				if(i%5==4){
       				    strArr.push(str);
       				    str = "";
       				}
       			}
       			$("#ol-news-main").html(strArr[0]);
                for( var i = 1 ; i < strArr.length ; i++ ){
                    $(`#newsCollapse${i}`).html(strArr[i]);
                }
       		},
       		error : function(e) {
       			alert("통신 실패");
       		}
       	})
    },
    save : function (target){
        var data = {
                    title : target.find(".title").text(),
                    originallink : target.find(".originallink").text(),
                    author : userEmail,
                    description : target.find(".description").text(),
                    pubdate : target.find(".pubdate").text()
                    };
        $.ajax({
            type : 'POST',
            url : '/api/v1/posts',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function(){
            alert('글이 등록되었습니다.');
            // window.location.href = '/posts/read?page=1';
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },
    criteria : function(targetId){
        let keyword;
        if(targetId == "it"){
            keyword = "인공지능";
        }else if(targetId == "entertainment"){
            keyword = "연예";
        }else if(targetId == "politics"){
            keyword = "정치";
        }
        return keyword;
    },
    check : function (target) {
        let title = target.find(".title").text();
        var data = {
                    title : target.find(".title").text()
                    };
        let isDuple;
        $.ajax({
            type : 'POST',
            url : '/api/v1/posts/check',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data),
            async: false
        }).done(function(result){
            isDuple =  result;
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
        return isDuple;
    }
}
main.init();