<template>
  <el-dialog title="个人信息" width="40%"
             :visible.sync="backupVisible" :close-on-click-modal="false" :modal=false>
  <el-form   :model="dataForm" label-width="80px">
    <el-form-item label="登录账号">
      <el-input v-model="dataForm.loginName" disabled ></el-input>
    </el-form-item>
    <el-form-item label="姓名" prop="userName">
      <el-input v-model="dataForm.userName" auto-complete="off" disabled></el-input>
    </el-form-item>
    <el-form-item label="性别" prop="gender" align="left">
      <el-radio-group v-model="dataForm.gender" disabled>
        <el-radio    :label=1  >男</el-radio>
        <el-radio    :label=2  >女</el-radio>
      </el-radio-group>
    </el-form-item>

    <el-form-item label="职称名称" prop="jobName">
      <el-input v-model="dataForm.jobName" disabled> </el-input>
    </el-form-item>
    <el-form-item label="出生日期" prop="birthday">
      <el-col :span="11">
        <el-date-picker type="date" placeholder="选择日期" disabled
                        v-model="dataForm.birthday" style="width: 100%;"></el-date-picker>
      </el-col>
    </el-form-item>
    <el-form-item label="科室名称" prop="deptName" >
      <el-input v-model="dataForm.deptName" disabled  auto-complete="off"></el-input>
    </el-form-item>
    <el-form-item>
      <!--<el-button type="primary" @click="onSubmit">立即创建</el-button>-->
      <el-button type="primary" @click.native="backupVisible = false">关闭</el-button>
    </el-form-item>
  </el-form>
  </el-dialog>
</template>

<script>
  export default {
    data() {
      return {
        backupVisible: false,
        jobTypeData: [{
          value: 0,
          label: '普通医生'
        }, {
          value: 1,
          label: '专家医生'
        }, {
          value: 2,
          label: '护士'
        }, {
          value: 3,
          label: '麻醉师'
        }, {
          value: 4,
          label: '其他'
        }],
        dataForm: {
          id: 0,
          userName: '',
          userCode:'',
          password: '123456',
          jobType:0,
          gender: 1,
        },
      }
    },
    methods: {
      // 设置可见性
      setBackupVisible: function (backupVisible,tempCode) {
        this.backupVisible = backupVisible
        this.findUserByCode(tempCode)
      },
      onSubmit() {
        console.log('submit!');
      },
      //获取用户对象
      findUserByCode: function (tempCode) {
        this.$api.user.findByDocCode({'name': tempCode})
          .then((res)=>{
            // this.dataForm = Object.assign({}, params.row)
            this.dataForm =  Object.assign({},res.data)
          })
      }
    }
  }
</script>

<style scoped>

</style>
