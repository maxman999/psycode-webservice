var main = {
    init : function () {
        let _this = this;
        _this.show();

        $('.ol-news').on('click','.btn-scrap',function(){
            let targetDiv = $(this).parent().parent().prev();
            let isDuple = _this.check(targetDiv);
            if(isDuple === false){
                _this.save(targetDiv);
            }else if(isDuple === true){
                alert("이미 등록된 기사입니다.");
            }
        });
        $("#keyword-search").on('click', function(){
            let keyword = $('#keyword-input').val();
            _this.show(keyword);
        })
        $(document).on('click', '.criteria' ,function(){
            let keyword = $(this).text();
            _this.show(keyword);
            $('#keyword-input').val(keyword);
        })
        $(document).on('click', '#recommend' ,function(){
            _this.getKeywords().then(function(keyword){
                _this.show(keyword);
                $("#keyword-input").val(keyword);
            });
        })
        $(document).on('click', '.no-criteria' ,function(){
            $('#settingModal').modal('show');
        })
    },

    show : function(inputkey) {
        var newsKeyword;
        if(inputkey == null){
            let items = $('.criteria').text();
            let keyArr = (items.split(" "));
            keyArr = keyArr.filter(Boolean);
            if(keyArr.length == 0){
                keyArr = ["인공지능","문화","연예","심리"];
            }
            newsKeyword = keyArr[Math.floor(Math.random() * keyArr.length)]
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
       		    let count = JSON.parse(result).total;
                if(count == 0){
                    if($("#ol-news-main").css("display") != "none"){
                        $("#ol-news-main, .ol-news, .more-btn").css("display","none");
                    }
                    $(".warning-wrapper").css("display", "inline");
                }else{
                    let news = JSON.parse(result).items;
                    let str = "";
                    let strArr = new Array();
                    for (var i = 0; i < news.length; i++) {
                        let pubdate = new Date(news[i].pubDate).toISOString().substring(0,10);
                        str += "<div><li class='list-group-item d-flex justify-content-between align-items-start list-group-item-action mb-2 shadow-sm rounded'>";
                        str += "<h6><span class='position-absolute top-5 start-0 translate-middle badge rounded-pill bg-dark'>"+ (i+1) +"<span class='visually-hidden'>news count</span></span></h6>"
                        str += "<div class='ms-2 me-auto' style='width: inherit'>";
                        str += "<div class='title fw-bold mb-1'>" + news[i].title + "</div>";
                        str += "<div class='description div-desc'>" + news[i].description + "</div>";
                        str += "<div class='pubdate' style='display: none'>" + pubdate + "</div>";
                        str += "<div class='originallink' style='display: none'>"+news[i].originallink+"</div>"
                        str += "</div><div class='col align-self-center'>";
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
                    $("#ol-news-main, .ol-news, .more-btn").css("display","flex");
                    $(".warning-wrapper").css("display", "none");
                }
                // 매끄러운 화면 전환을 위한 코드
                $('.main-div').css("display", "block");

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
                    useremail : userEmail,
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
    },
    getKeywords : function(){
        return new Promise(function(resolve){
            $.ajax({
                type : 'GET',
                url : 'http://ec2-15-165-82-52.ap-northeast-2.compute.amazonaws.com:5000/getKeyword',
                dataType : 'json',
                contentType : 'application/json; charset=utf-8',
            }).done(function(result){
                resolve(result.keyword);
            }).fail(function (error){
                alert(JSON.stringify(error));
            });
        })
    }
}
main.init();