<template>
  <div>
    <el-card>
      <div style="text-align: center; font-weight: bold; font-size: 18px">基于YOLOv5的目标检测</div>
    </el-card>
    <el-card style="margin: 10px 0">
      <div>
        <p style="font-size: 16px; font-weight: bold">上传图片</p>
        <el-upload
            action="http://localhost:8080/file/upload"
            :headers="{ token: user.token }"
            :on-success="handleImgSuccess"
            :file-list="fileList"
            list-type="picture-card">
          <i slot="default" class="el-icon-plus"></i>
          <div slot="file" slot-scope="{file}">
            <img class="el-upload-list__item-thumbnail" :src="file.url" alt="">
            <span class="el-upload-list__item-actions">
              <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
                <i class="el-icon-zoom-in"></i>
              </span>
              <span v-if="!disabled" class="el-upload-list__item-delete" @click="handleRemove(file)">
                <i class="el-icon-delete"></i>
              </span>
            </span>
          </div>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
        <el-button type="primary" style="margin-top: 10px" @click="detect">显示处理结果</el-button>
      </div>
    </el-card>
    <el-card style="margin: 10px 0;" class="el-card-detect">
      <div>
        <p style="font-weight: bold; font-size: 16px">检测结果</p>
        <div style="display: flex; flex-wrap: wrap;">
          <el-image
              v-for="(imageSrc, index) in detectResult"
              :key="index"
              style="width: 200px; height: 200px"
              :src="imageSrc"
              fit="contain"
              :preview-src-list="detectResult">
          </el-image>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "YOLOv5",
  data() {
    return {
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
      user: JSON.parse(localStorage.getItem('honey-user') || '{}'),
      fileList: [],
      detectResult: []
    };
  },
  methods: {
    detect() {
      // 使用map函数从fileList中提取每个文件上传后的URL
      const fileUrls = this.fileList.map(file => file.response.data);
      this.fileList=[]
      console.log(fileUrls)
      // 您可以调用一个API，将fileList里的文件信息发送给Spring Boot进行处理
      this.$request.post('/file/detect', fileUrls).then(res => {
        if(res.code === '200') {
          // 假设返回的数据是一个包含URL的数组
          this.detectResult = res.data
          console.log(this.detectResult)
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleImgSuccess(response, file, fileList) {
      this.fileList = fileList;
    },
    handleRemove(file) {
      this.fileList.splice(this.fileList.indexOf(file), 1);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    }
  }
}
</script>

<style scoped>
/deep/.el-card-detect{
  height: 300px;
}
</style>