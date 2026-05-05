#!/bin/bash
# User Management API - CURL Testing Script
# Run: bash users-curl.sh

BASE_URL="http://localhost:8080/api/v1/users"

echo "========================================="
echo "User Management API - CURL Testing"
echo "========================================="

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Test 1: Get all users
echo -e "\n${BLUE}Test 1: Get all users${NC}"
curl -X GET "$BASE_URL" -H "Content-Type: application/json"

# Test 2: Create a new user
echo -e "\n\n${BLUE}Test 2: Create a new user${NC}"
RESPONSE=$(curl -s -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "curl_test_user",
    "email": "curl.test@example.com",
    "password": "curl_password_123",
    "fullName": "Curl Test User",
    "active": true
  }')
echo "$RESPONSE"

# Extract the user ID from response (assuming jq is available)
if command -v jq &> /dev/null; then
  USER_ID=$(echo "$RESPONSE" | jq -r '.id')
  echo -e "\n${GREEN}Created user ID: $USER_ID${NC}"
else
  echo -e "\n${GREEN}(Note: Install 'jq' to automatically extract user ID)${NC}"
fi

# Test 3: Get user by username
echo -e "\n\n${BLUE}Test 3: Get user by username (admin)${NC}"
curl -X GET "$BASE_URL/username/admin" -H "Content-Type: application/json"

# Test 4: Check if username exists
echo -e "\n\n${BLUE}Test 4: Check if username exists (admin)${NC}"
curl -X GET "$BASE_URL/check/username/admin" -H "Content-Type: application/json"

# Test 5: Check if email exists
echo -e "\n\n${BLUE}Test 5: Check if email exists (admin@example.com)${NC}"
curl -X GET "$BASE_URL/check/email/admin@example.com" -H "Content-Type: application/json"

# Test 6: Create another test user for update/delete
echo -e "\n\n${BLUE}Test 6: Create user to test update/delete${NC}"
CREATE_RESPONSE=$(curl -s -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "update_test_user",
    "email": "update.test@example.com",
    "password": "update_password",
    "fullName": "Update Test User",
    "active": true
  }')
echo "$CREATE_RESPONSE"

# Extract ID if jq is available
if command -v jq &> /dev/null; then
  UPDATE_USER_ID=$(echo "$CREATE_RESPONSE" | jq -r '.id')

  # Test 7: Update user
  echo -e "\n\n${BLUE}Test 7: Update user (ID: $UPDATE_USER_ID)${NC}"
  curl -X PUT "$BASE_URL/$UPDATE_USER_ID" \
    -H "Content-Type: application/json" \
    -d '{
      "fullName": "Updated User Name",
      "active": false
    }'

  # Test 8: Delete user
  echo -e "\n\n${BLUE}Test 8: Delete user (ID: $UPDATE_USER_ID)${NC}"
  curl -X DELETE "$BASE_URL/$UPDATE_USER_ID" -H "Content-Type: application/json"

  echo -e "\n\n${GREEN}Deletion completed (204 No Content expected)${NC}"
fi

echo -e "\n\n${BLUE}All tests completed!${NC}"
echo "========================================="

