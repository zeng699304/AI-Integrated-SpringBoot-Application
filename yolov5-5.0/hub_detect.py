import torch

model = torch.hub.load('./', 'custom', 'yolov5s.pt', source='local')

img = "./data/images/zidane.jpg"

results = model(img)

results.show()