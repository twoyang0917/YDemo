import uuid

from flask import Flask, request, jsonify

app = Flask(__name__)


def md5(s):
    import hashlib
    return hashlib.md5(s.encode('utf-8')).hexdigest()


@app.route('/login', methods=['GET', 'POST'])
def login():
    username = request.form.get('username')
    password = request.form.get('password')
    sign = request.form.get('sign')
    if sign != md5(username + password + "magic"):
        return jsonify({'code': 1, 'msg': 'sign validation failed'})

    if username == 'admin' and password == md5('123456'):
        return jsonify({'code': 0, 'msg': 'success', 'token': str(uuid.uuid4())})

    return jsonify({'code': 1, 'msg': 'wrong username or password'})


app.run(host='0.0.0.0', port=5000)
