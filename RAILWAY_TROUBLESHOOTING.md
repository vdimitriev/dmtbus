# Railway Database Connection - Quick Fix

## Good News!
The application now automatically converts Railway's `DATABASE_URL` to the correct JDBC format. Railway always provides this variable when you add a PostgreSQL database.

## Problem
Application is trying to connect to `localhost:5432` instead of Railway's PostgreSQL database.

## Solution

### Step 1: Verify Database is Added

1. Go to your Railway project dashboard
2. You should see TWO services:
   - Your application service (dmtbus-app)
   - PostgreSQL database service

### Step 2: Ensure Spring Profile is Set

In your application service, under "Variables" tab, ensure this variable is set:

```
SPRING_PROFILES_ACTIVE = prod
```

### Step 3: Verify DATABASE_URL is Available (Railway does this automatically)

Railway automatically provides a `DATABASE_URL` variable to your application when the PostgreSQL service is in the same project. The application will automatically convert it from:
- `postgresql://user:password@host:port/database` (Railway format)
- to `jdbc:postgresql://user:password@host:port/database` (JDBC format)

To verify:
1. Click on your application service
2. Go to "Variables" tab
3. You should see `DATABASE_URL` listed (it will reference the PostgreSQL service)

If you DON'T see `DATABASE_URL`, then the services are not linked. Continue to Step 4.

### Step 4: Link Services (Only if DATABASE_URL is missing)

If `DATABASE_URL` is not showing in your application variables:

1. Delete the application service (don't worry, you can recreate it from GitHub)
2. In your Railway project, click "New" → "GitHub Repo"
3. Select your repository
4. Railway should auto-detect the Dockerfile and deploy
5. The `DATABASE_URL` should now be automatically available since both services are in the same project

### Alternative: Manual Variable Setup

If you prefer to use individual variables instead of `DATABASE_URL`:

1. Click on your PostgreSQL service
2. Go to "Connect" tab  
3. Click on "Variables" tab
4. Copy the reference format for each variable (e.g., `${{Postgres.PGHOST}}`)

5. Go to your application service → "Variables" tab
6. Add these variables:
   ```
   PGHOST = ${{Postgres.PGHOST}}
   PGPORT = ${{Postgres.PGPORT}}
   PGUSER = ${{Postgres.PGUSER}}
   POSTGRES_PASSWORD = ${{Postgres.PGPASSWORD}}
   PGDATABASE = ${{Postgres.PGDATABASE}}
   ```
   **Note**: Replace `Postgres` with your actual PostgreSQL service name

### Step 5: Redeploy

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

