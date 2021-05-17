<template>
  <div class="page-container">
	<!--工具栏-->
	<div class="toolbar" style="float:left;padding-top:10px;padding-left:15px;">
		<el-form :inline="true" :model="filters" :size="size">
			<el-form-item>
				<el-input v-model="filters.name" placeholder="登录账号"></el-input>
			</el-form-item>
			<el-form-item>
				<kt-button icon="fa fa-search" :label="$t('action.search')" perms="sys:role:view" type="primary" @click="findPage(null)"/>
			</el-form-item>
			<el-form-item>
				<kt-button icon="fa fa-plus" :label="$t('action.add')" perms="sys:user:add" type="primary" @click="handleAdd" />
			</el-form-item>
		</el-form>
	</div>
	<!--表格内容栏 <!--:showBatchDelete="false"-->
	<kt-table permsEdit="sys:user:edit" permsDelete="sys:user:delete"
		:data="pageResult" :columns="filterColumns" ref ="child"

		@findPage="findPage" @handleEdit="handleEdit" @handleDelete="handleDelete">
	</kt-table>
	<!--新增编辑界面-->
	<el-dialog :title="operation?'新增':'编辑'" width="50%" :visible.sync="dialogVisible" :close-on-click-modal="false">
		<el-form :model="dataForm" label-width="90px" :rules="dataFormRules"
      ref="dataForm" :size="size" style="text-align:left;">
			<el-form-item label="ID" prop="id" v-if="false" >
				<el-input v-model="dataForm.id" :disabled="true" auto-complete="off"></el-input>
			</el-form-item>
      <el-form-item label="登录账号" prop="loginName">
        <el-input v-model="dataForm.loginName" auto-complete="off"></el-input>
      </el-form-item>
			<el-form-item label="姓名" prop="userName">
				<el-input v-model="dataForm.userName" auto-complete="off"></el-input>
			</el-form-item>
			<el-form-item label="密码" prop="passWord">
				<el-input v-model="dataForm.passWord" type="password" auto-complete="off"></el-input>
			</el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-radio-group v-model="dataForm.gender">
          <el-radio    :label=1  >男</el-radio>
          <el-radio    :label=2  >女</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="职称" prop="jobName">
        <el-input v-model="dataForm.jobName" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="联系电话" prop="userPhone">
        <el-input v-model="dataForm.userPhone" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="出生日期" prop="birthday">
        <el-col :span="11">
          <el-date-picker type="date" placeholder="选择日期"
                          v-model="dataForm.birthday" style="width: 100%;"></el-date-picker>
        </el-col>
      </el-form-item>
			<el-form-item label="角色" prop="userRoles" v-if="!operation">
				<el-select v-model="dataForm.userRoles" multiple placeholder="请选择"
					 style="width: 100%;">
					<el-option v-for="item in roles" :key="item.id"
						:label="item.name" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
      <el-form-item label="人员介绍" prop="remark">
        <el-input :rows="4" v-model="dataForm.remark" auto-complete="off" type="textarea"></el-input>
      </el-form-item>
		</el-form>
		<div slot="footer" class="dialog-footer">
			<el-button :size="size" @click.native="dialogVisible = false">{{$t('action.cancel')}}</el-button>
			<el-button :size="size" type="primary" @click.native="submitForm"
                 :loading="editLoading">{{$t('action.submit')}}</el-button>
		</div>
	</el-dialog>
  </div>
</template>

<script>
import PopupTreeInput from "@/components/PopupTreeInput"
import KtTable from "@/views/Core/KtTable"
import KtButton from "@/views/Core/KtButton"
import { formatDateNotime } from "@/utils/datetime"
export default {
	components:{
		PopupTreeInput,
		KtTable,
		KtButton

	},
	data() {
		return {
			size: 'small',
			filters: {
				name: '',
			},
      myname:'调度',
      fullImageUrl: '',
      preImgPath:'',

			// columns: [],
			filterColumns: [
        {prop:"loginName", label:"登录名", minWidth:40 },
        {prop:"userName", label:"姓名", minWidth:50},
        {prop:"gender", label:"性别",  minWidth:30, formatter: this.genderFormat},
        {prop:"birthday", label:"出生日期", minWidth:80, formatter:this.dateFormat},
        {prop:"deptName", label:"所属单位", minWidth:60 },
        {prop:"userPhone", label:"联系电话", minWidth:60},
        {prop:"jobName", label:"职称", minWidth:60},
        {prop:"roleNames", label:"角色", minWidth:100}
        ],
			pageRequest: { pageNum: 1, pageSize: 10 },
			pageResult: {},

			operation: false, // true:新增, false:编辑
			dialogVisible: false, // 新增编辑界面是否显示
			editLoading: false,
			dataFormRules: {
        loginName: [
					{ required: true, message: '请输入登录账号', trigger: 'blur' }
				],
        userName: [
					{ required: true, message: '请输入姓名', trigger: 'blur' }
				],
        passWord: [
					{ required: true, message: '请输入密码', trigger: 'blur' }
				]
			},
			// 新增编辑界面数据
			dataForm: {
				id: null,
				name: '',
				password: '',
        jobType:0,
        gender: 1,
        userImg:'',
				userRoles: []
			},
			deptTreeProps: {
				label: 'name',
				children: 'children'
			},
			roles: [],
      dept:[],
    }
	},
	methods: {
    handleAvatarSuccess(res, file) {

      this.fullImageUrl = res.data+res.msg

      this.dataForm.userImg = res.msg
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png'  ;
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG或PNG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
		// 获取分页数据
		findPage: function (data) {
			if(data !== null) {
				this.pageRequest = data.pageRequest
			}
			this.pageRequest.columnFilters = {
			  loginName: {name:'', value:this.filters.name}}
			this.$api.user.findPage(this.pageRequest).then((res) => {
				this.pageResult = res.data
				this.findUserRoles()
			}).then( data!=null?data.callback:'')
        .catch((error)=>{
          this.$message.error(error)
          this.$refs.child.loading = false;
      })
		},
		// 加载用户角色信息
		findUserRoles: function () {
			this.$api.role.findAll().then((res) => {
				// 加载角色集合
				this.roles = res.data
			}).catch((error)=>{
        this.$message.error(error)
        this.$refs.child.loading = false;
      })
		},

		// 批量删除
    handleDelete: function (data) {
			this.$api.user.batchDelete(data.params).
      then(data!=null?data.callback:'')
        .catch((error)=>{
          this.$refs.child.loading = false;
          this.$message.error(error.message)
          }
        )
		},
		// 显示新增界面
		handleAdd: function () {
			this.dialogVisible = true
			this.operation = true
      this.fullImageUrl=''
			this.dataForm = {
        userRoles:[]
      }
		},
		// 显示编辑界面
		handleEdit: function (params) {
			this.dialogVisible = true
			this.operation = false
			this.dataForm = Object.assign({}, params.row)

			let userRoles = []
			for(let i=0,len=params.row.userRoles.length; i<len; i++) {
				userRoles.push(params.row.userRoles[i].roleId)
			}
			this.dataForm.userRoles = userRoles
		},
		// 编辑
		submitForm: function () {
			this.$refs.dataForm.validate((valid) => {
				if (valid) {
					this.$confirm('确认提交吗？', '提示', {}).then(() => {
						this.editLoading = true
						let params = Object.assign({}, this.dataForm)
						let userRoles = []
						for(let i=0,len=params.userRoles.length; i<len; i++) {
							let userRole = {
								userId: params.id,
								roleId: params.userRoles[i]
							}
							userRoles.push(userRole)
						}
						params.userRoles = userRoles
						this.$api.user.save(params).then((res) => {
							this.editLoading = false
							if(res.code == 200) {
								this.$message({ message: '操作成功', type: 'success' })
								this.dialogVisible = false
								this.$refs['dataForm'].resetFields()
							} else {
								this.$message({message: '操作失败, ' + res.msg, type: 'error'})
							}
							this.findPage(null)
						}) .catch(error=>{
						  this.$message.error(error+"====" + error.message)
						  this.editLoading = false;
            })
					})
				}
			})
		},

		// 菜单树选中
     deptTreeCurrentChangeHandle (data, node) {
        	this.dataForm.deptId = data.id
        	this.dataForm.deptName = data.name
		},
		// 时间格式化
      	dateFormat: function (row, column, cellValue, index){
          // console.log("rowgender",index )
          	return formatDateNotime(row[column.property])
      	},
       genderFormat: function (row, column) {
          // console.log("rowgender",row )
      return row.gender == 1 ? "男" : row.gender == 2 ? "女" : "未知";
      },
	},
	mounted() {
	}

}
</script>

<style scoped>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }

</style>
