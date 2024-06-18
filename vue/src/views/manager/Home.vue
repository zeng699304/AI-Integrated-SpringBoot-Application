<template>
  <div>
    <div class="header">
      早安，{{ user.name }}，祝你开心每一天！
    </div>
    <div class="content">
      <el-card class="welcome-card">
        <div slot="header" class="welcome-header">
          欢迎来到图像识别系统
        </div>
        <div class="main-content">
          <el-card class="intro-card">
            <div slot="header" class="intro-header">
              系统介绍
            </div>
            <div class="intro-text">
              <p>这是一个基于Spring Boot和YOLOv5的图像识别系统，您可以轻松上传图片并获得识别结果。</p>
              <p>我们的系统可以识别各种物体，并提供准确的分类与定位。</p>
              <p>此外，系统还集成了百度AI的文字识别和文本纠错功能。</p>
            </div>
          </el-card>
          <el-card class="start-card">
            <div slot="header" class="start-header">
              开始
            </div>
            <div class="start-buttons">
              <el-button type="primary" @click="$router.push('/yolov5')">图像识别</el-button>
              <el-button type="primary" @click="$router.push('/word')">文字识别</el-button>
              <el-button type="primary" @click="$router.push('/nlp')">文本纠错</el-button>
            </div>
          </el-card>
          <el-card class="announcement-card">
            <div slot="header" class="announcement-header">
              最新公告
            </div>
            <div class="announcement-text">
              <p>2024-06-18: 系统版本更新，修复了已知问题，提升了识别准确率。</p>
              <p>2024-06-01: 新增百度AI文字识别和文本纠错功能。</p>
            </div>
          </el-card>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('honey-user') || '{}'),
    };
  },
};
</script>

<style scoped>
.header {
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 20px;
  border-radius: 5px;
  margin-bottom: 20px;
  font-size: 18px;
  background-color: #f5f5f5;
  text-align: center;
}

.content {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.welcome-card {
  width: 100%;
}

.welcome-header {
  text-align: center;
  font-size: 24px;
  font-weight: bold;
}

.main-content {
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin-top: 20px;
}

.intro-card,
.start-card,
.announcement-card{
  margin: 5px;
  padding: 5px;
}

.intro-header,
.start-header {
  text-align: center;
  font-size: 20px;
  font-weight: bold;
}

.intro-text {
  font-size: 16px;
  line-height: 1.5;
  margin-top: 10px;
}

.start-buttons {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.start-buttons .el-button {
  margin: 10px 0;
}

.announcement-header{
  text-align: center;
  font-size: 20px;
  font-weight: bold;
}

.announcement-text{
  font-size: 16px;
  line-height: 1.5;
  margin-top: 10px;
}
</style>
