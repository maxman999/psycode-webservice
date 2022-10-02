var main = {
    init : function () {
        let _this = this;
        _this.show();

        $('.ol-news').on('click','.list-wrapper-div',handler);
        function handler(event){
            let ele = $(event.target);
            if(ele.is("div")){
                let url = $(this).find(".originallink").text();
                window.open(url);
            }else if(ele.is("button") || ele.is("i")){
                let isDuple = _this.check($(this));
                if(isDuple === false){
                    _this.save($(this));
                }else if(isDuple === true){
                    alert("이미 등록된 기사입니다.");
                }
            }
        }
        $("#keyword-search").on('click', function(){
            let keyword = $('#keyword-input').val();
            _this.show(keyword);
        })

        $("#keyword-input").on("keyup",function(key){
            if(key.keyCode==13) {
                let keyword = $('#keyword-input').val();
                _this.show(keyword);
            }
        });

        $(document).on('click', '.criteria' ,function(){
            let keyword = $(this).text();
            _this.show(keyword);
            $('#keyword-input').val(keyword);
        })

        $(document).on('click', '#recommend' ,function(){
            _this.getKeywords()
                .then(function(keyword){
                    _this.show(keyword);
                    $("#keyword-input").val(keyword);})
                .catch(error => {console.log(error)})
                .finally(()=>{$('#loading-div').css("display","none");});
        })

        $(document).on('click', '.no-criteria' ,function(){
            $('#settingModal').modal('show');
        })

        $('#news-toggle').on("click", function(){
            if($('#news-toggle i').attr("class") == "fas fa-chevron-up"){
                $('#news-toggle i').attr("class","fas fa-chevron-down");
            }else{
                $('#news-toggle i').attr("class","fas fa-chevron-up");
            }
        });

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
       		    let count = JSON.parse(result).display;
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
                        str += "<div class='list-wrapper-div'><li class='list-group-item d-flex justify-content-between align-items-start list-group-item-action mb-2 shadow-sm rounded'>";
                        str += "<h6><span class='position-absolute top-5 start-0 translate-middle badge rounded-pill bg-dark'>"+ (i+1) +"<span class='visually-hidden'>news count</span></span></h6>"
                        str += "<div class='ms-2 me-auto' style='width: inherit; word-break: break-all;'>";
                        str += "<div class='title fw-bold mb-1'>" + news[i].title + "</div>";
                        str += "<div class='description div-desc'>" + news[i].description + "</div>";
                        str += "<div class='pubdate' style='display: none'>" + pubdate + "</div>";
                        str += "<div class='originallink' style='display: none'>"+news[i].originallink+"</div>"
                        str += "</div><div class='col align-self-center'>";
                        str += "<div><button class='btn btn-sm btn-outline-secondary news-btn btn-scrap'><i class='fas fa-pencil-alt'></i></button></div>";
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
       			alert("요청을 처리할 수 없습니다.");
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
            url : '/api/v1/scraps',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function(){
            alert('글이 등록되었습니다.');
            // window.location.href = '/scraps/read?page=1';
        }).fail(function (error){
            alert("요청을 처리할 수 없습니다.");
        });
    },
    check : function (target) {
        let title = target.find(".title").text();
        var data = {
                    title : target.find(".title").text(),
                    useremail : userEmail  // news/read.html에서 타임리프로 선언한 값.
                    };
        let isDuple;
        $.ajax({
            type : 'POST',
            url : '/api/v1/scraps/check',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data),
            async: false
        }).done(function(result){
            isDuple =  result;
        }).fail(function (error){
            alert("요청을 처리할 수 없습니다.");
        });
        return isDuple;
    },
    getKeywords : function(){
        $('#loading-div').css("display","block");
        return new Promise(function(resolve,reject){
            $.ajax({
                type : 'GET',
                url : 'http://psy-code.com:5000/getKeyword?user_email='+userEmail, // userEmail은 thymeleaf를 이용해 선언한 값 read.html에서 선언하였다..
                dataType : 'json',
                contentType : 'application/json; charset=utf-8',
            }).done((result) => { resolve(result.keyword); })
            .fail((error) => { alert("요청을 처리할 수 없습니다. \n 스크랩북에 기사가 있는지 확인해주세요."); })
            .always(() => { $('#loading-div').css("display","none");
            });
        })
    }
}
main.init();