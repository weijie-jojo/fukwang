import axios from "axios";
import baseUrl from "../store/index";
import { Message } from "element-ui";
import Qs from "qs";
export default (obj) => {
  var myAxios = {};
  if (!obj.url) {
    Message("URL不能为空");
    return;
  }
  myAxios.method = !obj.type ? "post" : obj.type;
  myAxios.url = obj.url;
  myAxios.baseURL = baseUrl.state.baseURL;
  // myAxios.baseURL = "";
  if (obj.type == "get" && obj.data) {
    //如果是带参的get
    //删除data
    delete myAxios.data;
    //序列化data
    //改成params
    myAxios.params = Qs.parse(obj.data);
  } else {
    //如果是带参的post
    delete myAxios.params;
    myAxios.data = Qs.stringify(obj.data);
  }
  myAxios.withCredentials = true;
  var succ = obj.success;
  var success = function (resp) {
    if (resp.data.id == 0) {
      succ(resp.data);
    }
    if (resp.data.id == 1) {
      console.log(resp.data.message);
      Message(resp.data.message)
    }
  };
  console.log(myAxios);
  axios(myAxios)
    .then(success)
    .catch((error) => {
      console.log(error);
      Message.error("请联系管理员");
    });
};
