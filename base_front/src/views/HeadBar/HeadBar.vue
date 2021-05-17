<template>
  <div class="headbar" :style="{'background':themeColor}"
       :class="$store.state.app.collapse?'position-collapse-left':'position-left'">
    <!-- 导航收缩 -->
    <span class="hamburg">
      <el-menu class="el-menu-demo" :background-color="themeColor" text-color="#fff" :active-text-color="themeColor"
               mode="horizontal">
        <el-menu-item index="1" @click="onCollapse"><hamburger :isActive="collapse"></hamburger></el-menu-item>
      </el-menu>
    </span>
    <!-- 导航菜单 -->
    <span class="navbar">
      <el-menu :default-active="activeIndex" class="el-menu-demo"
               :background-color="themeColor" text-color="#fff" active-text-color="#ffd04b" mode="horizontal"
               @select="selectNavBar()">
        <el-menu-item index="1" @click="$router.push('/')"><i class="fa fa-home fa-lg"></i>  </el-menu-item>
        <!--<el-menu-item index="2" @click="openWindow('https://gitee.com/liuge1988/kitty')">{{$t("common.projectRepo")}}</el-menu-item>-->
        <!--<el-menu-item index="3" @click="openWindow('https://gitee.com/liuge1988/kitty/wikis/Home')">{{$t("common.doc")}}</el-menu-item>-->
        <!--<el-menu-item index="4" @click="openWindow('https://www.cnblogs.com/xifengxiaoma/')">{{$t("common.blog")}}</el-menu-item>-->
      </el-menu>
    </span>

    <span class="toolbar">
      <el-menu class="el-menu-demo" :background-color="themeColor" :text-color="themeColor"
               :active-text-color="themeColor" mode="horizontal">


        <el-menu-item index="2"   >


          <el-tooltip effect="dark" :content="fullscreen?`取消全屏`:`全屏`"
                      placement="bottom">
          <!--<i class="el-icon-rank grid-con-icon"  > </i>-->
            <div class="el-icon-rank grid-con-icon" @click="handleFullScreen">

            </div>
          </el-tooltip>

        </el-menu-item>

        <el-menu-item index="3"   >
              <!-- 消息中心 -->
                <div class="btn-bell">
                    <el-tooltip effect="dark"
                                content="查看消息"
                                 placement="bottom">
                        <router-link to="/fp/sysMsg">
                            <i class="el-icon-bell"></i>
                        </router-link>
                    </el-tooltip>
                    <!--<span class="btn-bell-badge" v-if="message"></span>-->
                </div>
        <!-- 系统时间 -->
          <!--<li class="user-info">-->
            <!--{{date}}</li>-->
        </el-menu-item>

        <el-menu-item index="4" v-popover:popover-personal>
          <!-- 用户信息 -->
          <span class="user-info"><img :src="user.avatar"/>
            {{user.userCode}}-{{user.userName}}</span>
          <el-popover ref="popover-personal" placement="bottom-end"
                      trigger="click" :visible-arrow="false">
            <personal-panel :user="user"></personal-panel>
          </el-popover>
        </el-menu-item>
      </el-menu>
    </span>
  </div>
</template>

<script>
  import {mapState} from 'vuex'
  // import mock from "@/mock/index"
  import Hamburger from "@/components/Hamburger"
  import ThemePicker from "@/components/ThemePicker"
  import LangSelector from "@/components/LangSelector"
  import Action from "@/components/Toolbar/Action"
  import NoticePanel from "@/views/Core/NoticePanel"
  import MessagePanel from "@/views/Core/MessagePanel"
  import PersonalPanel from "@/views/Core/PersonalPanel"
  import { format } from "@/utils/datetime"

  export default {
    components: {
      Hamburger,
      ThemePicker,
      LangSelector,
      Action,
      NoticePanel,
      MessagePanel,
      PersonalPanel
    },
    data() {
      return {
        user: {
          userName: "",
          avatar: "",
          userCode: "",
          registeInfo: format(new Date()),
          deptName:''
        },
        message: 2,
        activeIndex: '1',
        langVisible: false,
        fullscreen: false,
        date: format(new Date())
      }
    },
    methods: {
      openWindow(url) {
        window.open(url)
      },
      selectNavBar(key, keyPath) {
        console.log(key, keyPath)
      },
      // 折叠导航栏
      onCollapse: function () {
        this.$store.commit('onCollapse')
      },
      // 全屏事件
      handleFullScreen() {
        let element = document.documentElement;
        if (this.fullscreen) {
          if (document.exitFullscreen) {
            document.exitFullscreen();
          } else if (document.webkitCancelFullScreen) {
            document.webkitCancelFullScreen();
          } else if (document.mozCancelFullScreen) {
            document.mozCancelFullScreen();
          } else if (document.msExitFullscreen) {
            document.msExitFullscreen();
          }
        } else {
          if (element.requestFullscreen) {
            element.requestFullscreen();
          } else if (element.webkitRequestFullScreen) {
            element.webkitRequestFullScreen();
          } else if (element.mozRequestFullScreen) {
            element.mozRequestFullScreen();
          } else if (element.msRequestFullscreen) {
            // IE11
            element.msRequestFullscreen();
          }
        }
        this.fullscreen = !this.fullscreen;
      },

      // 切换主题
      onThemeChange: function (themeColor) {
        this.$store.commit('setThemeColor', themeColor)
      },
      // 语言切换
      changeLanguage(lang) {
        lang === '' ? 'zh_cn' : lang
        this.$i18n.locale = lang
        this.langVisible = false
      }
    },
    mounted() {
      this.sysName = "测试系统"
      var userStr = sessionStorage.getItem("user")
      if (userStr) {
        this.user = JSON.parse(userStr)
        // this.user.userName = user.userName
        this.user.avatar = require("@/assets/user1.png")
        this.user.registeInfo = format(this.user.createTime)
      }
      // let _this = this; //声明一个变量指向vue实例this,保证作用域一致
      // this.timer = setInterval(function () {
      //   _this.date = format(new Date());//修改数据date
      // }, 1000);
    },
    beforeDestroy() {
      if (this.timer) {
        clearInterval(this.timer);//在vue实例销毁钱，清除我们的定时器
      }
    },
    computed: {
      ...mapState({
        themeColor: state => state.app.themeColor,
        collapse: state => state.app.collapse
      })
    }
  }
</script>

<style scoped lang="scss">
  .headbar {
    position: fixed;
    top: 0;
    right: 0;
    z-index: 1030;
    height: 60px;
    line-height: 60px;
    border-color: rgba(180, 190, 190, 0.8);
    border-left-width: 1px;
    border-left-style: solid;
  }

  .hamburg, .navbar {
    float: left;
  }

  .toolbar {
    float: right;
  }

  .lang-item {
    font-size: 16px;
    padding-left: 8px;
    padding-top: 8px;
    padding-bottom: 8px;
    cursor: pointer;
  }

  .lang-item:hover {
    font-size: 18px;
    background: #b0d6ce4d;
  }

  .user-info {
    font-size: 20px;
    color: #fff;
    cursor: pointer;
    img {
      width: 40px;
      height: 40px;
      border-radius: 10px;
      margin: 10px 0px 10px 10px;
      float: right;
    }
  }

  .badge {
    line-height: 18px;
  }

  .position-left {
    left: 200px;
  }

  .position-collapse-left {
    left: 65px;
  }

  .header-right {
    float: right;
    padding-right: 50px;
  }

  .header-user-con {
    display: flex;
    height: 70px;
    align-items: center;
  }

  .btn-fullscreen {
    transform: rotate(45deg);
    /*margin-right: 5px;*/
    font-size: 30px;
  }

  .user-avator {
    margin-left: 20px;
  }

  .user-name {
    margin-left: 10px;
  }

  .user-avator img {
    display: block;
    width: 40px;
    height: 40px;
    border-radius: 50%;
  }

  .el-dropdown-link {
    color: #fff;
    cursor: pointer;
  }

  .el-dropdown-menu__item {
    text-align: center;
  }

  .grid-con-icon {
    font-size: 25px;
    width: 30px;
    height: 30px;
    text-align: center;
    line-height: 30px;
    color: #fff;
  }

  .btn-bell, .btn-fullscreen{
    position: relative;
    width: 30px;
    height: 30px;
    text-align: center;
    border-radius: 15px;
    cursor: pointer;
  }
  .btn-bell-badge{
    position: absolute;
    right: 0;
    top: -2px;
    width: 8px;
    height: 8px;
    border-radius: 4px;
    background: #f56c6c;
    color: #fff;
  }
  .btn-bell .el-icon-bell{
    color: #fff;
  }
</style>
