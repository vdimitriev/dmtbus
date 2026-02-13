# Deploying DMT Bus Application to Railway

## Prerequisites
- Railway account (sign up at https://railway.app)
- GitHub repository with your code

## Railway PostgreSQL Environment Variables

Railway automatically provides these environment variables when you add a PostgreSQL database:
- `PGHOST` - Database host
- `PGPORT` - Database port
- `PGUSER` - Database user
- `PGPASSWORD` - Database password (also available as `POSTGRES_PASSWORD`)
- `PGDATABASE` - Database name
- `DATABASE_URL` - Full connection string
- `RAILWAY_TCP_PROXY_DOMAIN` - TCP proxy domain
- `RAILWAY_TCP_PROXY_PORT` - TCP proxy port

## Application Configuration

The application is already configured to use Railway's environment variables. The configuration supports multiple formats:

1. **DATABASE_URL** (Railway's default full connection string)
2. **Individual variables**: `PGUSER`, `POSTGRES_PASSWORD`, `PGHOST`, `PGPORT`, `PGDATABASE`

### Configuration Priority (in application-prod.properties):

```properties
# URL: DATABASE_URL > SPRING_DATASOURCE_URL > default
spring.datasource.url=${DATABASE_URL:${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/dmtbus}}

# Username: PGUSER > DB_USERNAME > default
spring.datasource.username=${PGUSER:${DB_USERNAME:dmtbus}}

# Password: POSTGRES_PASSWORD > DB_PASSWORD > default
spring.datasource.password=${POSTGRES_PASSWORD:${DB_PASSWORD:dmtbus}}
```

## Deployment Steps

### Method 1: Deploy from GitHub (Recommended)

1. **Push your code to GitHub**
   ```bash
   git add .
   git commit -m "Prepare for Railway deployment"
   git push origin main
   ```

2. **Create a new project on Railway**
   - Go to https://railway.app
   - Click "New Project"
   - Select "Deploy from GitHub repo"
   - Choose your repository

3. **Add PostgreSQL Database**
   - In your Railway project, click "New"
   - Select "Database" → "Add PostgreSQL"
   - Railway will automatically create the database and set environment variables

4. **Configure the Application Service**
   - Railway should auto-detect your Dockerfile
   - Make sure the service is linked to the PostgreSQL database
   - Railway will automatically inject the database environment variables

5. **Set the Spring Profile** (if not already set in Dockerfile)
   - Go to your service settings
   - Add environment variable: `SPRING_PROFILES_ACTIVE=prod`

6. **Deploy**
   - Railway will automatically build and deploy your application
   - Monitor the build logs to ensure everything works correctly

### Method 2: Deploy using Railway CLI

1. **Install Railway CLI**
   ```bash
   npm install -g @railway/cli
   ```
   or
   ```bash
   curl -fsSL https://railway.app/install.sh | sh
   ```

2. **Login to Railway**
   ```bash
   railway login
   ```

3. **Initialize Railway project**
   ```bash
   railway init
   ```

4. **Add PostgreSQL**
   ```bash
   railway add --database postgresql
   ```

5. **Link the database to your service**
   ```bash
   railway link
   ```

6. **Deploy**
   ```bash
   railway up
   ```

## Verify Deployment

1. **Check logs**
   - In Railway dashboard, click on your service
   - Go to "Deployments" tab
   - View logs to ensure the application started successfully

2. **Test endpoints**
   - Railway will provide a public URL (e.g., `https://your-app.railway.app`)
   - Test your API endpoints:
     - `https://your-app.railway.app/actuator/health`
     - `https://your-app.railway.app/swagger-ui.html`

## Troubleshooting

### Database Connection Issues

If you see connection errors, verify:

1. **Database is linked to the service**
   - In Railway dashboard, ensure both services are in the same project
   - Check that environment variables are injected

2. **Check environment variables**
   - In your service settings, click "Variables"
   - Verify `DATABASE_URL`, `PGUSER`, and `POSTGRES_PASSWORD` exist

3. **Manual configuration (if needed)**
   If Railway's auto-configuration doesn't work, manually set:
   ```
   SPRING_DATASOURCE_URL=jdbc:postgresql://${RAILWAY_TCP_PROXY_DOMAIN}:${RAILWAY_TCP_PROXY_PORT}/${PGDATABASE}
   DB_USERNAME=${PGUSER}
   DB_PASSWORD=${POSTGRES_PASSWORD}
   ```

### Build Issues

1. **Dockerfile not detected**
   - Ensure `Dockerfile` is in the root directory
   - Check `railway.json` configuration

2. **Build timeout**
   - Railway has build time limits on free tier
   - Consider pre-building the JAR locally and using a simpler Dockerfile

## Environment Variables Reference

| Railway Variable | Application Property | Description |
|-----------------|---------------------|-------------|
| `DATABASE_URL` | `spring.datasource.url` | Full JDBC connection string |
| `PGUSER` | `spring.datasource.username` | Database username |
| `POSTGRES_PASSWORD` | `spring.datasource.password` | Database password |
| `PGHOST` | Part of connection URL | Database host |
| `PGPORT` | Part of connection URL | Database port |
| `PGDATABASE` | Part of connection URL | Database name |

## Cost Considerations

- **Free Tier**: Railway offers $5 free credit per month
- **PostgreSQL**: ~$5-10/month depending on usage
- **Application**: Billed based on resource usage (RAM, CPU)

## Additional Configuration

### Custom Domain
1. Go to your service settings in Railway
2. Click "Settings" → "Domains"
3. Add your custom domain

### Scaling
Railway automatically scales based on traffic, but you can configure:
- Memory limits
- CPU allocation
- Replicas (paid plans)

## Useful Commands

```bash
# View logs
railway logs

# Open service in browser
railway open

# Run commands in Railway environment
railway run <command>

# Check service status
railway status

# Deploy from CLI
railway up
```

## Next Steps

After successful deployment:
1. Set up monitoring (Railway provides basic metrics)
2. Configure custom domain (if needed)
3. Set up CI/CD for automatic deployments on git push
4. Add environment-specific variables (API keys, etc.)
5. Configure backups for PostgreSQL database

## Support

- Railway Documentation: https://docs.railway.app
- Railway Discord: https://discord.gg/railway
- Railway Status: https://status.railway.app

