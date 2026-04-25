from flask import Flask, jsonify

app = Flask(__name__)

@app.route('/')
def hello():
    return jsonify(message='Hello from CI/CD Pipeline App!')

@app.route('/health')
def health():
    return jsonify(status='ok')

@app.route('/api/data')
def data():
    return jsonify(items=['item1', 'item2', 'item3'])

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
