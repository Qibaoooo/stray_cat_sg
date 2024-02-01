From python:3.10

WORKDIR /user/src/app

COPY requirements.txt .

RUN pip install --upgrade pip
RUN pip install -r requirements.txt

COPY . .

ENTRYPOINT ["python","my_flask_app.py"]