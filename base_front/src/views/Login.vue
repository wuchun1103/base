<template>
  <div class="bg">
    <div class="login-wrap animated flipInY">
      <h3>欢迎访问测试系统</h3>
      <el-form ref="loginForm" :model="loginForm" :rules="loginRules"
                auto-complete="on" label-position="left" >
               <!--class="ms-content"-->

        <el-form-item prop="account">
          <el-input type="text" v-model="loginForm.account"
                    auto-complete="off" placeholder="账号"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" v-model="loginForm.password"
                    auto-complete="off" placeholder="密码"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button :loadilogin-tipsng="loading" type="primary"
                     @click.native.prevent="login('loginForm')">登录</el-button>
        </el-form-item>
        <el-form-item>
        <div class="">小提示 : 默认密码比较简单，建议登录后修改密码。</div>
        </el-form-item>
      </el-form>
    </div>

    <!-- 粒子漂浮物 -->
    <vue-particles
      :particle-opacity="0.7"
      :particles-number="30"
      :particle-size="5"
      :lines-width="2"
      :line-linked="true"
      :line-opacity="0.4"
      :lines-distance="150"
      :move-speed="3"
      :hover-effect="true"
      :click-effect="true"
      click-mode="push"
      color="#fff"
      shape-type="star"
      hover-mode="grab"
      lines-color="#fff"
    />
    <div class="beian_cs" >版权所有：江苏省沙雕计划科学技术研究所
      &nbsp;&nbsp;备案/许可证号：<a target="_blank" href="http://www.beian.miit.gov.cn">苏ICP备xxxxxx号</a></div>

  </div>
</template>

<script>
  import Cookies from "js-cookie"

  export default {
    name: 'Login',

    data () {
      return {
        loading: false,
        loginForm: {
          account: '',
          password: '',
        },
        loginRules: {
          account: [{required: true, trigger: 'blur', message: '请输入账号'}],
          password: [{required: true, trigger: 'blur', message: '请输入密码'}]
        }
      }
    },

    methods: {
      login(formName) {
        this.$refs[formName].validate((valid) => {
          if(valid) {
            this.loading = true
            let userInfo = {account:this.loginForm.account,
              password:this.loginForm.password}
            this.$api.login.login(userInfo).then((res) => {
              if(res.code != 200) {
                this.$message({
                  message: res.msg,
                  type: 'error'
                })
              } else {
                Cookies.set('zvfptoken', res.data.token) // 放置token到Cookie
                // sessionStorage.setItem('user', userInfo.account) // 保存用户到本地会话
                // console.log(res.data)
                // console.log(JSON.stringify(res.data))
                console.log("res.data",res.data);
                sessionStorage.setItem('user',  JSON.stringify(res.data) ) // 保存用户到本地会话
                this.$store.commit('menuRouteLoaded', false) // 要求重新加载导航菜单
                this.$router.push('/')  // 登录成功，跳转到主页
              }
              this.loading = false
            }).catch((res) => {
              this.loading = false
              this.$message({
                message: res.message,
                type: 'error'
              })
            });

          } else {
            return false;
          }
        });

      },
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .bg {
    position: fixed;
    overflow: hidden;
    height: 100%;
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    background: url('../assets/login_bg.jpg') -20% 10%;
    background-size: cover;
    top:0px;
    left:0px;
    #particles-js {
      position: absolute;
      top: 0;
      bottom: 0;
      left: 0;
      right: 0;
    }
  }
  .login-wrap {
    width: 300px;
    border-radius: 5px;
    padding: 20px;
    z-index: 3;
    margin-right: -37%;
    background: rgba(149, 159, 181, 0.5);
    .el-form-item {
      margin-bottom: 25px !important;
    }
    h3 {
      text-align: center;
      color: #ebedef;
      margin-top: 0px;
      margin-bottom: 5px;
      span {
        color: #20a0ff;
      }
    }
    form {
      margin-top: 25px;
      .el-form-item {
        margin-bottom: 15px;
      }
    }
    a {
      text-decoration: none;
      color: #1f2d3d;
    }
    button {
      width: 100%;
      font-weight: 600;
    }
  }
  .login-code-img {
    margin-left: 10px;
    margin-top: -4px;
    width: 110px;
    height: 40px;
  }
  .ms-content{
    padding: 30px 30px;
  }
  .login-tips{
    font-size:12px;
    line-height:30px;
    color:#fff;
    text-align: left;
  }
  .beian_cs{
    position: absolute;
    bottom: 0;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: white;
  }
</style>
