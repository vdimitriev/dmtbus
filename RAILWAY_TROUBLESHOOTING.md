# Railway Database Connection - Quick Fix

## Problem
Application is trying to connect to `localhost:5432` instead of Railway's PostgreSQL database.

## Solution

### Step 1: Verify Database is Added and Linked

1. Go to your Railway project dashboard
2. You should see TWO services:
   - Your application service (dmtbus-app)
   - PostgreSQL database service

3. **IMPORTANT**: Click on your application service, then click on "Variables" tab
4. Check if these variables exist:
   - `PGHOST`
   - `PGPORT`
   - `PGUSER`
   - `PGPASSWORD` or `POSTGRES_PASSWORD`
   - `PGDATABASE`

### Step 2: If Variables Are Missing - Link the Database

If the above variables are NOT showing in your application service:

1. Click on your PostgreSQL service
2. Go to "Connect" tab
3. Look for "Available Variables" section
4. You should see variables like `${{Postgres.PGHOST}}`, `${{Postgres.PGUSER}}`, etc.

5. Go back to your application service
6. Click "Variables" tab
7. Click "+ New Variable"
8. Add these variables **with references to the database service**:

   ```
   PGHOST = ${{Postgres.PGHOST}}
   PGPORT = ${{Postgres.PGPORT}}
   PGUSER = ${{Postgres.PGUSER}}
   POSTGRES_PASSWORD = ${{Postgres.PGPASSWORD}}
   PGDATABASE = ${{Postgres.PGDATABASE}}
   ```

   **Note**: Replace `Postgres` with the actual name of your PostgreSQL service if it's different.

### Step 3: Ensure Spring Profile is Set

In your application service, under "Variables" tab, ensure this variable is set:

```
SPRING_PROFILES_ACTIVE = prod
```

### Step 4: Redeploy

After setting the variables:
1. Go to "Deployments" tab in your application service
2. Click "Redeploy" on the latest deployment

OR

1. Make a small commit and push to GitHub (if using GitHub deployment)

## Verify Connection

After redeployment, check the logs:
1. Go to your application service
2. Click "View Logs"
3. Look for lines like:
   ```
   HikariPool-1 - Starting...
   HikariPool-1 - Added connection...
   ```
4. You should NOT see "Connection to localhost:5432 refused"

## Alternative: Use DATABASE_URL Directly

If linking doesn't work, Railway provides a `DATABASE_URL` variable. However, it's in PostgreSQL format, not JDBC format.

You can add these variables manually in your application service:

1. Get the `DATABASE_URL` from the PostgreSQL service (in "Connect" tab)
2. It will look like: `postgresql://user:pass@host:port/dbname`
3. Parse it and set individual variables OR use a startup script

## Still Not Working?

### Check Railway Service Logs
```bash
# Using Railway CLI
railway logs
```

### Check Database Service Status
Make sure the PostgreSQL service is running (green status in Railway dashboard)

### Verify Dockerfile
Make sure the Dockerfile CMD includes `--spring.profiles.active=prod`

### Manual Environment Variables (Last Resort)
If Railway's auto-linking doesn't work, you can manually copy the connection details:

1. Go to PostgreSQL service → "Connect" tab
2. Copy the connection details (host, port, user, password, database)
3. Set them as plain text variables in your application service:
   ```
   PGHOST = junction.proxy.rlwy.net
   PGPORT = 12345
   PGUSER = postgres
   POSTGRES_PASSWORD = your-password-here
   PGDATABASE = railway
   ```

**Warning**: This is less secure as values are hardcoded. Prefer using variable references.

## Contact Information
If still having issues, check:
- Railway Discord: https://discord.gg/railway
- Railway Documentation: https://docs.railway.app/guides/postgresql

