<template>
  <div>
    <div>
      <el-input placeholder="查询用户名" style="width: 200px; margin-right: 10px" v-model="username"></el-input>
      <el-input placeholder="查询姓名" style="width: 200px; margin-right: 10px" v-model="name"></el-input>
      <el-button type="primary" @click="load(1)">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <el-table @selection-change="handleSelectionChange" :data="tableData" stripe :header-cell-style="{ backgroundColor: 'aliceblue',fontWeight: 'bold', color: '#666' }">
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <el-table-column prop="id" label="序号" align="center" width="70"></el-table-column>
      <el-table-column prop="username" label="用户名" align="center"></el-table-column>
      <el-table-column prop="name" label="姓名" align="center"></el-table-column>
      <el-table-column prop="phone" label="手机号" align="center"></el-table-column>
      <el-table-column prop="email" label="邮箱" align="center"></el-table-column>
      <el-table-column prop="address" label="地址" align="center"></el-table-column>
      <el-table-column label="头像" align="center">
        <template slot-scope="scope">
          <el-image v-if="scope.row.avatar" :src="scope.row.avatar" style="width: 50px; height: 50px;border-radius: 50%" :preview-src-list="[scope.row.avatar]"></el-image>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleEdit(scope.row)" plain>编辑</el-button>
          <el-button type="danger" size="mini" plain @click="del(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px 0">
      <el-pagination
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-size="pageSize"
          layout="total, prev, pager, next"
          :total="total">
      </el-pagination>
    </div>
    <el-dialog title="编辑信息" :visible.sync="formVisible" width="30%">
      <el-form :model="form" label-width="80px" :rules="rules" ref="formRef">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="电话"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input type="textarea" v-model="form.address" placeholder="地址"></el-input>
        </el-form-item>
        <el-form-item label="头像" prop="avatar">
          <el-upload
              v-if="formVisible"
              class="avatar-uploader"
              action="http://localhost:8080/file/upload"
              :headers="{ token: user.token }"
              :file-list="uploadFileList"
              list-type="picture"
              :on-success="handleAvatarSuccess">
            <el-button type="primary" size="mini">上传头像</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-radio-group v-model="form.role">
            <el-radio label="管理员"></el-radio>
            <el-radio label="用户"></el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" align="center">
        <el-button @click="formVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "User",
  data() {
    return {
      tableData: [], // 表格数据
      pageNum: 1, // 当前页码
      pageSize: 6, // 每页显示条数
      username: '', // 查询用户名
      name: '', // 查询姓名
      total: 0,
      formVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('honey-user') || '{}'),
      rules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' },
        ]
      },
      ids: []
    }
  },
  created() {
    this.load()
  },
  computed: {
    uploadFileList() {
      // 如果form.avatar是一个字符串，创建一个包含单个文件对象的数组
      if (typeof this.form.avatar === 'string' && this.form.avatar) {
        return [{
          name: 'avatar', // 文件名可以是任意字符串
          url: this.form.avatar, // 文件的URL
          // 其他必要的属性，如uid
        }];
      }
      return []; // 如果form.avatar不是字符串，返回一个空数组
    },
  },
  methods: {
    delBatch(){
      if(!this.ids.length){
        this.$message.error('请选择要删除的数据')
        return
      }
      this.$confirm('您确认批量删除这些数据吗','确认删除',{type: "warning"}).then(response => {
        this.$request.delete('/user/delete/batch', {data: this.ids}).then(res => {
          if(res.code === '200'){
            this.$message.success('删除成功')
            this.load()
          }else{
            this.$message.error(res.msg)
          }
        })
      })
    },
    handleSelectionChange(val){
      this.ids = val.map(item => item.id)
    },
    del(id){
      this.$confirm('您确认删除吗','确认删除',{type: "warning"}).then(response => {
        this.$request.delete('/user/delete/' + id).then(res => {
          if(res.code === '200'){
            this.$message.success('删除成功')
            this.load()
          }else{
            this.$message.error(res.msg)
          }
        })
      })
    },
    handleEdit(row){ //编辑数据
      this.form = JSON.parse(JSON.stringify(row)) //给form赋值 注意要深拷贝术据
      // 确保form.avatar是一个字符串
      if (typeof this.form.avatar !== 'string') {
        this.form.avatar = ''; // 如果不是字符串，则清空它
      }
      this.formVisible = true
    },
    handleAdd(){ //新增数据
      this.form = {role: '用户'} //新增数据的时候清空form
      this.formVisible = true
    },
    save(){ //保存按钮触发的逻辑
      this.$refs.formRef.validate((valid) => {
        if(valid){
          this.$request({
            url: this.form.id ? '/user/update' : '/user/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res=>{
            if(res.code === '200') { //表示保存成功
              this.$message.success('保存成功')
              this.load(1)
              this.formVisible = false
              //触发父级的数据更新
              this.$emit('update-user')
            }else{
              this.$message.error(res.msg)
            }
          })
        }
     })
    },
    handleAvatarSuccess(response,file,fileList){
      // 把user的头像属性设置为上传成功后的图片地址
      this.form.avatar = response.data
    },
    reset(){
      this.username = ''
      this.name = ''
      this.load()
    },
    load(pageNum){  //分页查询
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/user/selectByPage',{
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          name: this.name
        }
      }).then(res=>{
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    handleCurrentChange(pageNum){
      this.load(pageNum)
    }
  }
}
</script>

<style scoped>

</style>