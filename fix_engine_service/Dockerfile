FROM python:3.11-slim

WORKDIR /app
COPY . .

RUN apt-get update && apt-get install -y \
    build-essential \
    libquickfix-dev \
    && rm -rf /var/lib/apt/lists/*

RUN pip install -r requirements.txt

RUN mkdir -p store/logs

CMD ["uvicorn", "fastapi_main:app", "--host", "0.0.0.0", "--port", "9000"]

