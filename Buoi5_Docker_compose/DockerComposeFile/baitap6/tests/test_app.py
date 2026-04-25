import pytest
import sys
sys.path.insert(0, '/app')
from app import app

@pytest.fixture
def client():
    app.config['TESTING'] = True
    with app.test_client() as client:
        yield client

def test_home(client):
    response = client.get('/')
    assert response.status_code == 200
    data = response.get_json()
    assert 'message' in data

def test_health(client):
    response = client.get('/health')
    assert response.status_code == 200
    data = response.get_json()
    assert data['status'] == 'ok'

def test_api_data(client):
    response = client.get('/api/data')
    assert response.status_code == 200
    data = response.get_json()
    assert 'items' in data
    assert len(data['items']) == 3
