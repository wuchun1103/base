<template>
  <el-dialog title="修改密码" width="40%"
             :visible.sync="backupVisible" :close-on-click-modal="false" :modal=false>
    <el-form status-icon ref="dataForm"   :rules="dataFormRules"  :model="dataForm" label-width="80px">
  <el-form-item label="原密码" prop="password">
    <el-input type="password" v-model="dataForm.password" auto-complete="off" placeholder="原密码"></el-input>
  </el-form-item>
      <el-form-item label="新密码" prop="newpassword">
    <el-input type="password" v-model="dataForm.newpassword" auto-complete="off" placeholder="新密码"></el-input>
  </el-form-item>
      <el-form-item label="确认密码">
    <el-input type="password" v-model="dataForm.cfmpassword" auto-complete="off" placeholder="确认新密码"></el-input>
  </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="backupVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitForm('dataForm')">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
    export default {
      data() {
        var validatePass = (rule, value, callback) => {

          if (value === '') {
            callback(new Error('请输入旧密码'));
          }else {
            callback();
          }
          // else {
          //   if (this.dataForm.checkPass !== '') {
          //     this.$refs.dataForm.validateField('checkPass');
          //   }
          //   callback();
          // }
        };
        var  validatePass2 =(rule, value, callback) => {
          if (value === '') {
            callback(new Error('请输入新密码'));
          } else if (value !== this.dataForm.cfmpassword) {
            callback(new Error('确认密码与新密码不一致!'));
          } else {
            callback();
          }
        };
          return {
            backupVisible: false,
            dataForm: {
              password:'',
              newpassword:'',
              cfmpassword:''
            },
            dataFormRules: {
              password: [
                { validator:  validatePass, trigger: 'blur'  }
              ],
              newpassword: [
                { validator:  validatePass2, trigger: 'blur'   }
              ]
            }
          }
      },
      methods: {
        // 设置可见性
        setPassVisible: function (backupVisible ) {
          this.backupVisible = backupVisible
        },
        submitForm(formName) {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              this.$api.user.updatePassword({'password':this.dataForm.password,
              'newPassword':this.dataForm.newpassword})
                .then((res)=>{
                  if(res.code==200) {
                    this.$message.success("修改成功")
                    this.backupVisible=false
                  } else
                    this.$message.error(res.msg)
                })
            } else {
              // console.log('error submit!!');
              return false;
            }
          });
        }
      }
    }
</script>

<style scoped>

</style>
