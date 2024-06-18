from flask import Flask, request, jsonify
import torch
from PIL import Image
from io import BytesIO
import os
import time

app = Flask(__name__)
# 配置模型路径和权重
model = torch.hub.load('./', 'custom', 'yolov5s.pt', source='local')

# 设置Spring Boot项目的文件保存路径
springboot_files_path = "../files"
detect_dir_base = os.path.join(springboot_files_path, "detect")
os.makedirs(detect_dir_base, exist_ok=True)

# 初始化批次号
batch_number = 0

@app.route('/predict', methods=['POST'])
def predict():
    global batch_number
    files = request.files.getlist('file')
    if not files:
        return jsonify({'error': 'no files'}), 400

    # 为这批图像创建一个新的目录
    batch_dir_name = f"exp{batch_number}"
    batch_dir_path = os.path.join(detect_dir_base, batch_dir_name)
    os.makedirs(batch_dir_path, exist_ok=True)

    # 处理完这批图像后，批次号加一
    batch_number += 1

    urls = []
    for file in files:
        # 将上传的文件转换为图像
        image = Image.open(BytesIO(file.read()))
        # 进行推理
        results = model(image)

        # 保存处理后的图像，这里我们直接使用原始文件名
        results.render()  # 确保结果已渲染
        save_path = os.path.join(batch_dir_path, file.filename)
        for img in results.imgs:
            img_pil = Image.fromarray(img)
            img_pil.save(save_path)

        # 构建每个文件的完整URL
        url = f'detect/{batch_dir_name}/{file.filename}'
        urls.append(url)

    # 返回URL列表给前端
    return jsonify({'urls': urls})


if __name__ == '__main__':
    app.run()