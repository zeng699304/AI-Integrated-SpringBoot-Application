<template>
  <div>
    <div>
      <el-card>
        <div style="text-align: center; font-weight: bold; font-size: 18px">基于百度AI的NLP文本纠错</div>
      </el-card>
      <el-card style="margin: 10px 0">
        <div>
          <p style="font-size: 16px; font-weight: bold">待纠错文本</p>
          <el-input
              type="textarea"
              :autosize="{ minRows: 4, maxRows: 8}"
              placeholder="请输入待纠错文本"
              v-model="words">
          </el-input>
          <el-button type="primary" style="margin-top: 10px" @click="correction">纠错</el-button>
        </div>
      </el-card>
      <el-card style="margin: 10px 0;" class="el-card-detect">
        <div>
          <p style="font-weight: bold; font-size: 16px">纠错结果</p>
          <el-input
              type="textarea"
              :autosize="{ minRows: 4, maxRows: 8}"
              placeholder="已纠错文本"
              v-model="input_words">
          </el-input>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "NLP",
  data() {
    return {
      words: '',
      input_words: '',
    }
  },
  methods: {
    correction() {
      this.$request.get('/nlp',{
        params: {
          text: this.words
        }
      }).then(res => {
        this.input_words = res.data
      })
    }
  }
}
</script>

<style scoped>
/deep/.el-card-detect{
  height: 300px;
}
</style>