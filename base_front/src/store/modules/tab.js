export default {
  state: {
    // 主入口标签页
    mainTabs: [],
    // 当前标签页名
    mainTabsActiveName: ''
  },
  mutations: {
    updateMainTabs (state, tabs) {
      state.mainTabs = tabs
       // console.log("tabs",tabs)

    },
    updateMainTabsActiveName (state, name) {
      state.mainTabsActiveName = name
      // console.log("tabActive",name)
    }
  }
}
