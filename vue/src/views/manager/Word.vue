<template>
  <div>
    <div>
      <el-card>
        <div style="text-align: center; font-weight: bold; font-size: 18px">基于百度AI的图像文字识别</div>
      </el-card>
      <el-card style="margin: 10px 0">
        <div>
          <p style="font-size: 16px; font-weight: bold">上传图片</p>
          <el-upload
              :headers="{ token: user.token }"
              class="upload-demo"
              drag
              action="http://localhost:8080/word"
              :on-success="handleSuccess">
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
          <el-button type="primary" style="margin-top: 10px" @click="correction">显示识别结果</el-button>
        </div>
      </el-card>
      <el-card style="margin: 10px 0;" class="el-card-detect">
        <div>
          <p style="font-weight: bold; font-size: 16px">识别结果</p>
          <el-input
              type="textarea"
              :autosize="{ minRows: 4, maxRows: 8}"
              placeholder="识别后的文本"
              v-model="words">
          </el-input>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "Word",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('honey-user') || '{}'),
      input_words: '',
      words:''
    }
  },
  methods: {
    handleSuccess(response, file, fileList){
      this.input_words = response.data
    },
    correction() {
      this.words = this.input_words
    }
  }
}
</script>

<style scoped>
/deep/.el-card-detect{
  height: 300px;
}
</style>