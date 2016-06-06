var items_per_page = 20;    //默认每页显示20条数据
var pageIndex = 0;
var records = 0;
var startPer = 0;
var endPer = 20;
var typepage = 1;
var skipPagePer;
var userName ;
var userziurl;
$(document).ready(function () {
    /*----------------------基本信息----------------------------*/
    var firstData = $("#modelData").val();
    var userData = $("#userData").val();

    //用户
    if(userData != undefined && userData != ""){
        var json = JSON.parse(userData);
        userName=json.userName;
        var userzi=""
        var userziOne;

        var userziImage=json.img;
        $.each(json.baiduAccounts, function (i, item) {
            userziurl = item.openDomains
            userziOne = item.baiduUserName;
            userzi = userzi+"<div class='item account_item' name='userets' useinfo='"+item.openDomains+"'>"+item.baiduUserName+"</div>"
        });
        $("#userNameinfo").empty();
        $("#userNameinfo").append(json.userName)

        var htmluser = "<div class='text' id='account_box'>"+userziOne+"</div><i class='dropdown icon'></i><div class='menu' id='account_list'>"+userzi+"</div>";
        $("#useName").val(userName)
        var returnUrl = $("#returnUrl").val();
        if(returnUrl != undefined && returnUrl != "" && returnUrl != null){
            $("#inputValue").val(returnUrl);
        }else{
            $("#inputValue").val(userziurl);
        }
        $("#account").empty();
        $("#account").append(htmluser);

        var htmluserImg = "<IMG SRC='data:image/gif;base64,"+userziImage+"' ALT='Larry'>";
        $("#imagesTitle").empty();
        $("#imagesTitle").append(htmluserImg);
    }

    $("div[name=userets]").each(function(i,o){
        $(o).click(function(){
            var url = $(this).attr("useinfo");
            var name = $(this).html()
            $("#account_box").html(name);
            var returnUrl = $("#returnUrl").val();
            if(returnUrl != undefined && returnUrl != "" && returnUrl != null){
                $("#inputValue").val(returnUrl);
            }else{
                $("#inputValue").val(url);
            }

            $("#useName").val(userName);
        });
    });

    if (firstData != undefined && firstData != "") {
        var json = JSON.parse(firstData);
        $.each(json.websiteBasic, function (i, item) {
            if (json.websiteBasic != undefined && json.websiteBasic != null && json.websiteBasic != "") {
                $("#titleData").empty();
                var htmlTitle = "<tr><td class='positive'>网站Title：</td><td class='fifteen wide'>" + ((item.titleData == undefined) ? "--" : item.titleData) + "</td></tr>"
                    + "<tr><td class='positive'>世界排名：</td><td>" + ((item.worldRank == undefined) ? "--" : item.worldRank) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + ((item.worldalexa == undefined) ? "--" : item.worldalexa) + "</td></tr>"
                    + "<tr><td class='positive'>域名年龄：</td><td>" + ((item.createdate == undefined) ? "--" : item.createdate) + "</td></tr>"
                    + "<tr><td class='positive' rowspan='2'><b>seo信息：</b></td><td>"+item.googlePR+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+item.baiduBR+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + ((item.seoInfoKZ == undefined) ? "--" : item.seoInfoKZ) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + ((item.seoInfoWL == undefined) ? "--" : item.seoInfoWL) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + ((item.seoInfoSY == undefined) ? "--" : item.seoInfoSY) + "</td></tr>"
                    + "<tr><td>" + ((item.baiduLU == undefined) ? "--" : item.baiduLU) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + ((item.seo_link == undefined) ? "--" : item.seo_link) + "</td></tr>"
                $("#titleData").append(htmlTitle);
            }
        });

        //基本信息搜索引擎信息
        $("#searchInfo").empty();
        var htmlSearch;
        $.each(json.searchInfo, function (i, items) {
            if (json.searchInfo != undefined && json.searchInfo != null && json.searchInfo != "") {
                htmlSearch = htmlSearch + "<tr><td class='positive'>" + getSearchImg(items.searchNum) + "<b>" + ((items.searchBD == undefined) ? "--" : items.searchBD) + "</b></td><td>" + ((items.numberBD == undefined) ? "--" : items.numberBD) + "</td><td>" + ((items.backNumberBD == undefined) ? "--" : items.backNumberBD) + "</td></tr>";
            }
        });
        $("#searchInfo").append(htmlSearch);

        $.each(json.contentInfo, function (i, itemse) {
            if (json.contentInfo != undefined && json.contentInfo != null && json.contentInfo != "") {
                $("#titleContenNUM").empty();
                $("#titleConten").empty();
                $("#titleContenNUM").append(itemse.titleNum + " 个字符");
                $("#titleConten").append(itemse.titleMsg);

                $("#keyworldNUM").empty();
                $("#keyworld").empty();
                $("#keyworldNUM").append(itemse.keywordsNum + " 个字符");
                $("#keyworld").append(itemse.keywords);

                $("#messageNUM").empty();
                $("#message").empty();
                $("#messageNUM").append(itemse.descNum + " 个字符");
                $("#message").append(itemse.desc);
            }
        });
    }
    /*--------------------------end---------------------------------*/
/////////////////////详细信息  数据////////////////
    var jsonDataDet = $("#jsonDataDet").val();
    if (jsonDataDet != undefined && jsonDataDet != "") {
        var json = JSON.parse(jsonDataDet);
        var htmlDet = "";

        $.each(json, function (i, items) {
            var reData = getDesc(items.code);
            var startTD ;
            if(items.score > 0){
                var re = "^\d+$|^\d+\.\d+$";
                var String = new RegExp("\\d+(\\.\\d+)?");
                var number = reData[0].match(String)[0];
                if(items.score == number){
                    startTD = "<td class='positive'>"
                }else{
                    startTD = "<td class='warning'>"
                }
            }else{
                startTD = "<td class='error'>"
            }
            htmlDet = htmlDet + "<tr>"+startTD+"<i class='question circular icon link' data-content='" + reData[1] + "'></i><b>" + items.name + "</b></td><td>" + items.score + "</td><td>" + items.problem + "</td><td>" + items.desc + "</td><td>" + reData[0] + "</td></tr>";
        });
        $("#dataDet").empty();
        $("#dataDet").append(htmlDet);
        $('.question').popup();
    }

    /******************pagination*********************/

    var pageSelectCallback = function (page_index, jq) {
        if (typepage == 1) {
            $("#pagination1").append("<span style='margin-right:10px;'>跳转到 <input id='anyPageNumber1' type='text' class='price'/></span>&nbsp;&nbsp;<a href='javascript:skipPagePer();' class='page_go'> GO</a>");
        }
        if (pageIndex == page_index) {
            return false;
        }
        pageIndex = page_index;
        if (typepage == 1) {
            startPer = (page_index + 1) * items_per_page - items_per_page;
            endPer = (page_index + 1) * items_per_page;
            getreportInfo();
        }
        return false;
    };

    var getOptionsFromForm = function (current_page) {


        var opt = {callback: pageSelectCallback};
        opt["items_per_page"] = items_per_page;
        opt["current_page"] = current_page;
        opt["prev_text"] = "上一页";
        opt["next_text"] = "下一页";

        //avoid html injections
        var htmlspecialchars = {"&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;"};
        $.each(htmlspecialchars, function (k, v) {
            opt.prev_text = opt.prev_text.replace(k, v);
            opt.next_text = opt.next_text.replace(k, v);
        });
        return opt;
    };
    var optInit = getOptionsFromForm(0);

    skipPagePer = function () {
        var _number = 0;
        if (typepage == 1) {
            _number = $("#anyPageNumber1").val() - 1;
            if (_number <= -1 || _number == pageIndex) {
                return;
            }
            $("#pagination1").pagination(records, getOptionsFromForm(_number));
        }
    };
    /**********************************************************************************/

    /*---------------------深度分析---------------------------*/

    var getreportInfo = function(){
        var username = $("#useName").val();
        $.ajax({
            url: "/seo/getReportInfo",
            type: "POST",
            dataType: "json",
            data:{
                limit: endPer,
                startPer: startPer,
                userName: username
            },
            success: function (data) {
                var html ="";
                $.each(data.rows, function (i, item) {
                    if (item != undefined && item != "" && item != null) {
                        var divhtml;
                        if(item.type == 1){
                            divhtml = "<div doumName='doum' class='ui green button' uid="+item.uuid+">下载</div>"
                        }else{
                            divhtml = "--"
                        }
                        html = html + "<tr><td class='positive'>" + item.dateString + "</td><td><a href='javascript:' uname='upUrl' title='点击连接查看详细信息' uid = " + item.uuid + ">" + item.url + "</a></td><td>"+divhtml+"</td></tr>";
                        $("#liebiao").empty();
                        $("#liebiao").append(html);
                    }
                });
                records = data.countData;
                typepage = 1;
                $("#pagination1").pagination(data.countData, getOptionsFromForm(pageIndex));
            }
        });
    }
    if($("#returnNum").val() == 2){
        getreportInfo();
    }


    //下载
    $("body").on("click", "div[doumName=doum]",function(){
        var nameid = $(this).attr("uid");
        var username = $("#useName").val();
        location.href = "/seo/downSeoReport?uid="+nameid+"&userName="+username;
        /*$.ajax({
            url:"/seo/downSeoReport",
            type:"GET",
            dataType:"json",
            data:{
                uid:nameid,
                userName:username
            },
            success: function (){
                alert("成功");
            }
        })*/
    });

    /**分析结果详细信息查看**/
    $("body").on("click", "a[uname=upUrl]", function () {
        var uid = $(this).attr("uid");
        var username = $("#useName").val();
        $.ajax({
            url: "/seo/getReportPageInfo",
            type: "POST",
            dataType: "json",
            data: {
                uid: uid,
                userName:username
            },
            success: function (data) {
                var flag = JSON.stringify(data.flag);
                if(flag == undefined || flag.replace(/\"+/g,"") != 0){
                    var htmldata = "";
                    $.each(data.resultData, function (i, item) {
                        var jsondata = JSON.parse(item);
                        $.each(data.resultInfo, function (i, items) {
                            var jsoninfo = JSON.parse(items);

                            htmldata = "<tr><td class='positive'><i class='question circular icon link z-idx' data-content='" + jsoninfo.SEO1_1_question + "'></i><b>" + jsoninfo.SEO1_1 + "</b></td><td>" + jsondata.SEO1_1 + "</td><td>" + jsoninfo.SEO1_1_problems + "</td><td>" + getUrlPaiLie(jsondata.SEO1_1_problems) + "</td><td>" + jsondata.SEO1_1_num + "</td><td>" + jsoninfo.SEO1_1_scores + "</td></tr>"
                            + "<tr><td class='positive'><i class='question circular icon link z-idx' data-content='" + jsoninfo.SEO1_3_question + "'></i><b>" + jsoninfo.SEO1_3 + "</b></td><td>" + jsondata.SEO1_3 + "</td><td>" + jsoninfo.SEO1_3_problems + "</td><td>" + getUrlPaiLie(jsondata.SEO1_3_problems) + "</td><td>" + jsondata.SEO1_3_num + "</td><td>" + jsoninfo.SEO1_3_scores + "</td></tr>"
                            + "<tr><td class='positive'><i class='question circular icon link z-idx' data-content='" + jsoninfo.SEO1_4_question + "'></i><b>" + jsoninfo.SEO1_4 + "</b></td><td>" + jsondata.SEO1_4 + "</td><td>" + jsoninfo.SEO1_4_problems + "</td><td>" + getUrlPaiLie(jsondata.SEO1_4_problems) + "</td><td>" + jsondata.SEO1_4_num + "</td><td>" + jsoninfo.SEO1_4_scores + "</td></tr>"
                            + "<tr><td class='positive'><i class='question circular icon link z-idx' data-content='" + jsoninfo.SEO1_6_question + "'></i><b>" + jsoninfo.SEO1_6 + "</b></td><td>" + jsondata.SEO1_6 + "</td><td>" + jsoninfo.SEO1_6_problems + "</td><td>" + getUrlPaiLie(jsondata.SEO1_6_problems) + "</td><td>" + jsondata.SEO1_6_num + "</td><td>" + jsoninfo.SEO1_6_scores + "</td></tr>"
                            + "<tr><td class='positive'><i class='question circular icon link z-idx' data-content='" + jsoninfo.SEO1_7_question + "'></i><b>" + jsoninfo.SEO1_7 + "</b></td><td>" + jsondata.SEO1_7 + "</td><td>" + jsoninfo.SEO1_7_problems + "</td><td>" + getUrlPaiLie(jsondata.SEO1_7_problems) + "</td><td>" + jsondata.SEO1_7_num + "</td><td>" + jsoninfo.SEO1_7_scores + "</td></tr>"
                            + "<tr><td class='positive'><i class='question circular icon link z-idx' data-content='" + jsoninfo.SEO1_8_question + "'></i><b>" + jsoninfo.SEO1_8 + "</b></td><td>" + jsondata.SEO1_8 + "</td><td>" + jsoninfo.SEO1_8_problems + "</td><td>" + getUrlPaiLie(jsondata.SEO1_8_problems) + "</td><td>" + jsondata.SEO1_8_num + "</td><td>" + jsoninfo.SEO1_8_scores + "</td></tr>"
                            + "<tr><td class='positive'><i class='question circular icon link z-idx' data-content='" + jsoninfo.SEO1_9_question + "'></i><b>" + jsoninfo.SEO1_9 + "</b></td><td>" + jsondata.SEO1_9 + "</td><td>" + jsoninfo.SEO1_9_problems + "</td><td>" + getUrlPaiLie(jsondata.SEO1_9_problems) + "</td><td>" + jsondata.SEO1_9_num + "</td><td>" + jsoninfo.SEO1_9_scores + "</td></tr>"
                            + "<tr><td class='positive'><i class='question circular icon link z-idx' data-content='" + jsoninfo.SEO1_10_question + "'></i><b>" + jsoninfo.SEO1_10 + "</b></td><td>" + jsondata.SEO1_10 + "</td><td>" + jsoninfo.SEO1_10_problems + "</td><td>" + getUrlPaiLie(jsondata.SEO1_10_problems) + "</td><td>" + jsondata.SEO1_10_num + "</td><td>" + jsoninfo.SEO1_10_scores + "</td></tr>"
                            + "<tr><td class='positive'><i class='question circular icon link z-idx' data-content='" + jsoninfo.SEO1_11_question + "'></i><b>" + jsoninfo.SEO1_11 + "</b></td><td>" + jsondata.SEO1_11 + "</td><td>" + jsoninfo.SEO1_11_problems + "</td><td>" + getUrlPaiLie(jsondata.SEO1_11_problems) + "</td><td>" + jsondata.SEO1_11_num + "</td><td>" + jsoninfo.SEO1_11_scores + "</td></tr>"

                        });
                    });
                    $("#dataModal").empty();
                    $("#dataModal").append(htmldata);
                    $('.question').popup();
                    $('.ui.modal').modal("show");
                }else{
                    alert("报告正在分析中请稍后再来查看");
                }

            }
        });



    });


    /**分析提交操作**/
    $("#start").click(function () {
        var urlPath = $("#inputValue").val();
        var username = $("#useName").val();
        $.ajax({
            url: "/seo/domainAjax",
            type: "POST",
            dataType: "json",
            data: {
                urlPath: urlPath,
                userName:username
            },
            success: function (data) {
                var a = 1;
                $.each(data, function (i, item) {
                    if (a == 1) {
                        if (item.dateFalg == "0") {
                            alert("距上次提交任务不足半小时，请在" + item.dateTime + "的半小时后再次提交任务");
                        } else if (item.dateFalg == "1") {
                            alert("分析任务已提交，分析完成后会在列表中显示以方便查看");
                        } else if (item.dateFalg == "2") {
                            alert("你的域名地址出现问题，请检查后重新提交分析任务")
                        }
                    }
                    i++;
                });
            }
        });
    });


    /*------------------------end----------------------------*/
/////////////////////页面控制/////////////
    $("#inputValue").keydown(function (e) {
        var ev = document.all ? window.event : e;
        if (ev.keyCode == 13) {
            var url = $("#inputValue").val();
            url = url.replace(/。+/g, ".").replace(/\.+/g, ".");
            $("#inputValue").val(url);
            var strRe = IsURL(url);
            if (!strRe) {
                $("#msg").show(500);
                setTimeout('$("#msg").hide(500);', 2000);
                return false;
            } else {
                $("#msg").empty();
                $("#returnNum").val(1);
                $("#lodingser").show();
                $("#frmSubmit").submit();
            }

        }
    });
    $("div[cname='btnSubmit']").click(function () {
        var search = $(this).attr("Search");
        var url = $("#inputValue").val();
        url = url.replace(/。+/g, ".").replace(/\.+/g, ".");
        if(search == 2){
            if(userziurl == undefined || userziurl == ""){
                $("#inputValue").val(url);
            }else{
                $("#inputValue").val(userziurl);
            }
        }else{
            $("#inputValue").val(url);
        }

        var strRe = IsURL(url);
        if (strRe) {
            $("#returnNum").val(search);
            if (search == 2) {
                $("#frmSubmit").attr("action", "/seo/domain");
            } else {
                $("#frmSubmit").attr("action", "/seo/score");
            }
            $("#frmSubmit").submit();
            $("#lodingser").show();
            $("#msg").empty();
        } else {
            $("#msg").show(500);
            setTimeout('$("#msg").hide(500);', 2000);
            return false;
        }

    });


});



//验证用户输入
/*function checkSubmit(flag) {
 return flag;
 }*/
function IsURL(str_url) {
    var chinase = ".*[\u4e00-\u9fa5]+.*$";
    var strRegex = "^(((https|http)?://)?([a-zA-Z0-9]([a-zA-Z0-9\-]{0,61}[a-zA-Z0-9]))?\.+(com|top|cn|wang|net|org|hk|co|cc|me|pw|la|asia|biz|mobi|gov|name|info|tm|tv|tel|us|tw|website|host|press|cm|tw|sh|ws|in|io|vc|sc|ren))$";
    var regexIP = "^(((https|http)?://)?(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])?\.+(com|top|cn|wang|net|org|hk|co|cc|me|pw|la|asia|biz|mobi|gov|name|info|tm|tv|tel|us|tw|website|host|press|cm|tw|sh|ws|in|io|vc|sc|ren))$";
    var ip = new RegExp(regexIP);
    var re = new RegExp(strRegex);
    var chinaString = new RegExp(chinase);
    if (chinaString.test(str_url)) {
        return (false);
    } else {
        if (ip.test(str_url)) {
            return (false);
        } else {
            if (re.test(str_url)) {
                return (true);
            } else {
                return (false);
            }
        }
    }

}
function getSearchImg(num) {
    if (num == "1") {
        return "<img src='/images/ico_baidu.gif'>";
    } else if (num == "2") {
        return "<img src='/images/ico_360so.gif'>"
    } else if (num == "3") {
        return "<img src='/images/ico_sogou.gif'>"
    } else if (num == "4") {
        return "<img src='/images/ico_sousou.gif'>"
    } else if (num == "5") {
        return "<img src='/images/ico_bing.gif'>"
    }
}
if (!!window.ActiveXObject || "ActiveXObject" in window) {
    $('#dropdown').click(function () {
        if ($("#menu").css("display") == "none") {//隐藏
            $(this).find('#menu').show();
            $("#menu ").mouseleave(function () {
                $("#menu").css("display", "none");
            });
            $('.menu_item').click(function () {
                $('#menu_text').html($(this).text());
                $('#menu').hide();
            });
        }
        else {
            $("#menu").hide();
        }
    });
} else {
    $('.ui.dropdown').dropdown();
}

function getDesc(title) {
    var returnString = Array();
    switch (title) {
        case "SEO2_2":
            returnString.push("4分");
            returnString.push("网站收录量是指搜索引擎收录一个网站的页面数量。");
            break;
        case "SEO2_4":
            returnString.push("3.6分");
            returnString.push("网站url地址中包含拼音目标关键字的比例。");
            break;
        case "SEO2_5":
            returnString.push("3.6分");
            returnString.push("域名年龄是指你的域名注册时间的长短，越老的网站越容易获得好的排名。");
            break;
        case "SEO2_7":
            returnString.push("3.2分");
            returnString.push("域名后缀是指你的域名结尾是否属于顶级后戳，顶级后戳更容易获得好的排名。（例：best-ad.cn，cn就是后戳）");
            break;
        case "SEO3_1":
            returnString.push("5.5分");
            returnString.push("域名主页目标关键字在外部链接文本中占的比例。");
            break;
        case "SEO3_2":
            returnString.push("5.5分");
            returnString.push("此项主要是指网站的外部链接的数量和外部链接来源的域名数，应尽可能多获得来自多个域名的多个链接。");
            break;
        case "SEO3_5":
            returnString.push("4.2分");
            returnString.push("外部链接增长数率的百分比。");
            break;
        case "SEO3_6":
            returnString.push("3.8分");
            returnString.push("外部连接年龄是指你的外部链接域名注册时间的长短，外部链接域名的注册时间越早对排名越好。");
            break;
    }
    return returnString;
}

function getUrlPaiLie(url){
    url = JSON.stringify(url).replace(/\"+/g,"").replace("[","").replace("]","");
    var urls = url.split(",");
    var returnUrls=""
    for(var i=1 ; i<=urls.length; i++){
        if(i%2==0){
            returnUrls = returnUrls + urls[i-1] +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+"<br/>";
        }else{
            returnUrls = returnUrls + urls[i-1] +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        }

    }
    return returnUrls;
}
//时间提示
var now = new Date(), hour = now.getHours();
var time = document.getElementById('time');
if (0 < hour && hour < 6) {
    time.innerHTML = "凌晨,好！"
}
else if (hour < 9) {
    time.innerHTML = "早上,好！"
}
else if (hour < 12) {
    time.innerHTML = "上午,好！"
}
else if (hour < 14) {
    time.innerHTML = "中午,好！"
}
else if (hour < 18) {
    time.innerHTML = "下午,好！"
}
else if (hour < 23) {
    time.innerHTML = "晚上,好！"
}
else if (hour == 24) {
    time.innerHTML = "凌晨,好！"
}
else {
    time.innerHTML = "晚上,好！"
}
if (!!window.ActiveXObject || "ActiveXObject" in window) {
    $('#account').click(function () {
        if ($("#account_list").css("display") == "none") {//隐藏
            $('#account_list').show();
            $("#account_list ").mouseleave(function () {
                $("#account_list").css("display", "none");
            });
            $('.account_item').click(function () {
                $('#account_box').html($(this).text());
                $('#account_list').hide();
            });
        }
        else {
            $("#account_list").hide();
        }
    });
} else {
    $('.ui.dropdown').dropdown();
}