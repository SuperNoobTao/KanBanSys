var process_request = "<img src='loading.gif' width='16' height='16' border='0' align='absmiddle'>正在数据处理中...";
var username_empty = "<span style='COLOR:#ff0000'>   账号不能为空!</span>";
var username_shorter = "<span style='COLOR:#ff0000'>  账号长度不能少于 3 个字符。</span>";
var username_longer = "<span style='COLOR:#ff0000'>  账号长度不能大于 30个字符。</span>";
var username_invalid = "- 用户名只能是由字母数字以及下划线组成。";
var username_have_register = "<span style='COLOR:#ff0000'> 账号已经存在,请重新输入!</span>";
var username_can_register="<span style='COLOR:#006600'> √ 恭喜您 ! 该账号可以注册!</span>";
var password_empty = "<span style='COLOR:#ff0000'> × 登录密码不能为空。</span>";
var password_shorter_s = "<span style='COLOR:#ff0000'>  登录密码不能少于 6 个字符。</span>";
var password_shorter_m = "<span style='COLOR:#ff0000'>  登录密码不能多于 30 个字符。</span>";
var confirm_password_invalid = "<span style='COLOR:#ff0000'>  两次输入密码不一致!</span>";
var email_empty = "<span style='COLOR:#ff0000'>  邮箱不能为空！</span>";
var email_invalid = "<span style='COLOR:#ff0000'>  邮箱格式出错！</span>";
var email_have_register = "<span style='COLOR:#ff0000'>  该邮箱已被注册! </span>";
var email_can_register = "<span style='COLOR:#006600'> √ 邮箱可以注册!</span>";
var agreement_no = "<span style='COLOR:#ff0000'>  您没有接受协议</span>";
var agreement_yes= "<span style='COLOR:#006600'> √ 已经接受协议</span>";
var info_can="<span style='COLOR:#006600'> √ 可以注册!</span>";
var info_right="<span style='COLOR:#006600'> √ 填写正确!</span>";

var name_flag=false;
var email_flag=false;
var password_flag=false;
var accept_flag=false;




/*
 * 获取工程的路径
 */
function getRootPath() {
    var pathName = window.location.pathname.substring(1);
    var webName = pathName == '' ? '' : pathName.substring(0, pathName
        .indexOf('/'));
    return window.location.protocol + '//' + window.location.host + '/'
        + webName + '/';
}
/*
 * 账号检测
 */
function checkAccount(obj) {
    if (checks(obj.value) == false) {
        showInfo("username_notice", username_invalid);
    } else if (obj.value.length < 1) {
        showInfo("username_notice", username_empty);
    }else if (obj.value.length < 3) {
        showInfo("username_notice", username_shorter);
    } else if(obj.value.length>30){
        showInfo("username_notice", username_longer);
    }else {
        // 调用Ajax函数,向服务器端发送查询
        $.ajax({ //一个Ajax过程
            type: "post", //以post方式与后台沟通
            url :"/check", //与此页面沟通
            dataType:'json',//返回的值以 JSON方式 解释
            data: 'account='+obj.value, //发给的数据
            success: function(json){//如果调用成功
                if(json.flag){
                    showInfo("username_notice", username_have_register);
                }else {
                    showInfo("username_notice", username_can_register);
                    name_flag=true;
                    return;
                }
            }
        });
    }
    name_flag=false;
}


/*
 * 注册检测
 */
function checkUserRigister(obj) {

    // 调用Ajax函数,向服务器端发送查询
    $.ajax({ //一个Ajax过程
        type: "post", //以post方式与后台沟通
        url :"/register", //与此页面沟通
        data: $("#signupform").serialize(), //发给的数据
        success: function(json){//如果调用成功
            if(json.code=='200'){
                alert("注册成功");
                window.location.href="/login";
            }else {
                alert("注册失败");
            }
        },
        error: function(data) {
            alert("注册失败");
        }
    });
}






/*
 * 用户名检测是否包含非法字符
 */
function checks(t) {
    szMsg = "[#%&\'\"\\,;:=!^@]"
    for (i = 1; i < szMsg.length + 1; i++) {
        if (t.indexOf(szMsg.substring(i - 1, i)) > -1) {
            return false;
        }
    }
    return true;
}




/*
 * 检测密码强度检测
 */
function checkIntensity(pwd)
{
    var Mcolor = "#FFF",Lcolor = "#FFF",Hcolor = "#FFF";
    var m=0;

    var Modes = 0;
    for (i=0; i<pwd.length; i++)
    {
        var charType = 0;
        var t = pwd.charCodeAt(i);
        if (t>=48 && t <=57)
        {
            charType = 1;
        }
        else if (t>=65 && t <=90)
        {
            charType = 2;
        }
        else if (t>=97 && t <=122)
            charType = 4;
        else
            charType = 4;
        Modes |= charType;
    }

    for (i=0;i<4;i++)
    {
        if (Modes & 1) m++;
        Modes>>>=1;
    }

    if (pwd.length<=4)
    {
        m = 1;
    }

    switch(m)
    {
        case 1 :
            Lcolor = "2px solid red";
            Mcolor = Hcolor = "2px solid #DADADA";
            break;
        case 2 :
            Mcolor = "2px solid #f90";
            Lcolor = Hcolor = "2px solid #DADADA";
            break;
        case 3 :
            Hcolor = "2px solid #3c0";
            Lcolor = Mcolor = "2px solid #DADADA";
            break;
        case 4 :
            Hcolor = "2px solid #3c0";
            Lcolor = Mcolor = "2px solid #DADADA";
            break;
        default :
            Hcolor = Mcolor = Lcolor = "";
            break;
    }
    document.getElementById("pwd_lower").style.borderBottom  = Lcolor;
    document.getElementById("pwd_middle").style.borderBottom = Mcolor;
    document.getElementById("pwd_high").style.borderBottom   = Hcolor;
}



/*
 * 公用程序
 */
function showInfo(target,Infos){
    document.getElementById(target).innerHTML = Infos;
}
function showclass(target,Infos){
    document.getElementById(target).className = Infos;
}

function motaikuang_suc(){
            $("#mymodal").modal("toggle");
    }

function motaikuang_fail(){
    $("#mymodal").modal("toggle");
}




