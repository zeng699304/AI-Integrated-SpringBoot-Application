# **基于springboot的AI体验栈平台**
## 项目简介

这个项目是一个集成了多种人工智能技术的全栈应用，基于Spring Boot框架实现了高扩展性的后端服务。主要功能包括：

- **基于YOLOv5的目标检测**：实现实时目标检测，准确识别图像中的物体。
- **基于百度AI的图像文字识别**：通过百度AI接口，快速识别图像中的文字内容。
- **基于百度AI的NLP文本纠错**：利用百度AI的自然语言处理技术，提供高效的文本纠错功能。

该项目不仅展示了Spring Boot在构建现代化后端服务中的强大能力，还结合了前沿的人工智能技术，提供了丰富的用户体验。

## 技术栈

- **后端框架**：Spring Boot
- **前端框架**：Vue.js
- **目标检测**：YOLOv5
- **图像文字识别**：百度AI OCR
- **文本纠错**：百度AI NLP
- **数据库**：MySQL
- **其他**： Maven、flask、Element

## 项目搭建与配置

### 环境要求

- JDK 17+
- Node.js 14+
- MySQL 5.7+

### 后端项目搭建

1. 克隆项目到本地

   ```
   git clone https://github.com/zeng699304/AI-Integrated-SpringBoot-Application.git
   ```

2. 进入项目目录并使用Maven构建项目，若显示无JDK需自己添加

3. 导入并配置数据库

   - 将ssm.sql文件导入navicat

   - 在`src/main/resources/application.yml`文件中配置你的MySQL数据库连接信息。

4. 配置百度AI

   - 注册百度AI账号并创建应用，获取API Key和Secret Key。
   - 在`src/main/java/com/example/springboot/utils/AiUtils.java`文件中配置你的百度AI API Key和Secret Key。

5. 从SpringbootApplication启动Spring Boot应用

### 前端项目搭建

1. 进入前端项目目录

   ```
   cd vue
   ```

2. 安装依赖

   ```
   npm install
   ```

3. 启动前端项目

   ```
   npm run serve
   ```

### YOLOv5运行

1. pycharm打开yolov5-5.0

2. 安装所需依赖

   ```
   pip install -r requirements.txt
   ```

3. 安装flask

4. 运行flask.api文件

### 使用说明

- 访问`http://localhost:8081`查看项目主页。
- 使用YOLOv5进行目标检测，上传图片即可识别图像中的物体。
- 使用百度AI OCR进行图像文字识别，上传图片即可识别文字内容。
- 使用百度AI NLP进行文本纠错，输入文本即可进行纠错。



欢迎大家点赞、fork，并在Issues中提出你们的宝贵意见！
