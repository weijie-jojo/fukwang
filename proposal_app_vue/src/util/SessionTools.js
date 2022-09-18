export default {
  /**
   * 存到sessionStorage
   * @param key 键
   * @param value 值
   */
  saveObjSessionStr(key, value) {
    window.sessionStorage.setItem(key, JSON.stringify(value));
  },

  /**
   * 把字典列表存到sessionStorage
   * @param dictList 字典列表
   */
  saveDict(dictList) {
    this.saveObjSessionStr("dict", dictList);
  },

  /**
   * 从sessionStory取数据
   * @param key 键
   */
  getObjSessionStr(key) {
    var obj = window.sessionStorage.getItem(key);
    if (obj != "undefined") {
      return JSON.parse(obj);
    }
    return null;
  },

  /**
   * 取字典对象
   */
  getDicts() {
    return this.getObjSessionStr("dict");
  },

  /**
   * 通过别名(分组)和状态ID获取到具体字典对象
   * @param alias 别名（分组）
   * @param typeId 状态ID
   * @returns {具体字典对象(JSON)}
   */
  getDict(alias, typeId) {
    var list = this.getDicts();
    var dict = list
      .filter((item) => item.typeAlias === alias)
      .find((item) => item.typeId === typeId);
    return dict;
  },

  /**
   * 通过别名获取对应的字典对象列表(例如：性别下拉框用)
   * @param alias 别名
   * @returns {字典对象列表}
   */
  getDictList(alias) {
    var list = this.getDicts();
    var dicts = list.filter((item) => item.typeAlias == alias);
    return dicts;
  },

  /**
   * 输入别名和状态名获得状态ID
   * @param alias 别名
   * @param typeName 状态名（启用、禁用）
   * @returns {状态表示的ID}
   */
  getTypeId(alias, typeName) {
    var list = this.getDicts();
    var typeId = list
      .filter((item) => item.typeAlias === alias)
      .find((item) => item.typeName === typeName).typeId;
    return typeId;
  },
  getTypeName(alias, typeId) {
    var list = this.getDict(alias, typeId);
    var typeName = list.typeName;
    return typeName;
  },

  saveUser(user) {
    this.saveObjSessionStr("user", user);
  },

  getUser() {
    var user = this.getObjSessionStr("user");
    return user;
  },

  saveTypeList(typeList) {
    this.saveObjSessionStr("typeList", typeList);
  },

  getTypeList() {
    return this.getObjSessionStr("typeList");
  },

  getType(typeSuffix) {
    return this.getTypeList().find((item) => item.typeSuffix == typeSuffix);
  },

  getStateList() {
    return this.getDictList("state");
  },

  getState(typeId) {
    if (!typeId) {
      return "无";
    }
    return this.getDict("state", typeId).typeName;
  },

  saveObjSession2Str(key, value) {
    window.sessionStorage.setItem(key, JSON.stringify(value));
  },
  // 取出数据
  getObjSession2Str(key) {
    return JSON.parse(window.sessionStorage.getItem(key));
  },
  // 专门用来保存字典对象的
  saveDicts(value) {
    this.saveObjSession2Str("dict", value);
  },

  // 获取字典中对应别名和id的对象
  getZn4Dict(alias, typeId) {
    // 1.获取字典对象
    var list = this.getObjSession2Str("dict");
    // 2.通过别名找到所有的 对应别名相关的信息
    // filter ： 过滤找到所有别名相同的数据集合
    // find ： 找到集合中的某一个条件相符合的数据
    var dict = list
      .filter((item) => item.typeAlias == alias)
      .find((item) => item.typeId == typeId);
    return dict;
  },
  // 通过别名和id获取类型的名称
  getTypeName4Dict(alias, typeId) {
    var dict = this.getZn4Dict(alias, typeId);
    return dict ? dict.typeName : "";
  },
  // 获取字典中对应别名的对象
  getZn4Dict2(alias) {
    var list = this.getObjSession2Str("dict");
    var dict = list.filter((item) => item.typeAlias == alias);
    console.log("字典表数据" + dict);
    return dict;
  },
  // 通过别名获取类型的数组
  getTypeName4Dict2(alias) {
    var dict = this.getZn4Dict2(alias);
    return dict;
  },
};
